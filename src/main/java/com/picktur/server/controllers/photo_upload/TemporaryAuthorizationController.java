package com.picktur.server.controllers.photo_upload;

import com.picktur.server.configurations.security.UserPrincipal;
import com.picktur.server.constants.FileResolution;
import com.picktur.server.entities.Authorization;
import com.picktur.server.entities.AuthorizationKind;
import com.picktur.server.entities.User;
import com.picktur.server.entities.dto.photo_upload.TemporaryAuthorizationDto;
import com.picktur.server.entities.photo_upload.TemporaryPhoto;
import com.picktur.server.relations.TemporaryAuthorized;
import com.picktur.server.relations.UserAuthorized;
import com.picktur.server.repositories.documents.AuthorizationRepo;
import com.picktur.server.repositories.documents.TemporaryPhotoRepo;
import com.picktur.server.repositories.documents.UserRepo;
import com.picktur.server.repositories.relationships.TemporaryAuthorizedRepo;
import com.picktur.server.repositories.relationships.UserAuthorizedRepo;
import com.picktur.server.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/api/authorization_controller")
public class TemporaryAuthorizationController {

    @Autowired
    UserRepo userRepo;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private AuthorizationRepo authorizationRepo;
    @Autowired
    private TemporaryAuthorizedRepo temporaryAuthorizedRepo;
    @Autowired
    private TemporaryPhotoRepo temporaryPhotoRepo;
    @Autowired
    private UserAuthorizedRepo userAuthorizedRepo;

    @PostMapping(value = "/upload_authorization", consumes = {MediaType.MULTIPART_MIXED_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AuthorizationUploadResponse> uploadAuthorization(@AuthenticationPrincipal UserPrincipal principal, @RequestPart String caption, @RequestPart String authorizationKind, @RequestPart(value = "files", required = true) final MultipartFile file) {

        User user = userRepo.findById(principal.getId()).get();
        TemporaryAuthorizationDto temporaryAuthorizationDto = null;

        authorizationKind = authorizationKind.toUpperCase();
        AuthorizationKind kind = null;

        switch (authorizationKind) {
            case "SUBJECT":
                kind = AuthorizationKind.SUBJECT;
                break;
            case "PROPERTY":
                kind = AuthorizationKind.PROPERTY;
                break;
            case "BRAND":
                kind = AuthorizationKind.BRAND;
                break;
            case "COPYRIGH":
                kind = AuthorizationKind.COPYRIGH;
                break;
        }

        try {
            temporaryAuthorizationDto = uploadAndPersistFile(file, user, caption, kind);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new AuthorizationUploadResponse(temporaryAuthorizationDto), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/add_authorization_to_photo_ids", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity joinAuthorization(@AuthenticationPrincipal UserPrincipal principal, @RequestBody AuthorizationUploadRequest request) {

        ArrayList<String> authorizedPhotosIds = request.getAuthorizedPhotos();
        String authorizationId = request.getAuthorizationId();
        Authorization authorization = authorizationRepo.findById(authorizationId).get();

        authorizedPhotosIds.forEach(api -> {
            TemporaryPhoto tp = temporaryPhotoRepo.findById(api).get();
            TemporaryAuthorized temporaryAuthorized = new TemporaryAuthorized(authorization, tp);
            temporaryAuthorizedRepo.save(temporaryAuthorized);
        });
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }


    /*@PostMapping("/add_authorizations_to_photo_ids")
    public ResponseEntity addAuthorizationsToPhotoId(@RequestBody AuthorizationsAddRemoveRequest request) {

        ArrayList<String> authorizedPhotosIds = request.getPhotoIds();
        ArrayList<String> authorizationIds = request.getAuthorizationIds();

        authorizationIds.forEach(ai -> {
            Authorization authorization = authorizationRepo.findById(ai).get();

            authorizedPhotosIds.forEach(api -> {
                TemporaryPhoto tp = temporaryPhotoRepo.findById(api).get();
                TemporaryAuthorized temporaryAuthorized = new TemporaryAuthorized(authorization, tp);
                temporaryAuthorizedRepo.save(temporaryAuthorized);
            });
        });


        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
*/

    @PostMapping("/remove_authorization_to_photo_ids")
    public ResponseEntity removeAuthorizationsToPhotoId(@RequestBody AuthorizationUploadRequest request) {

        //request.getAuthorizationIds().forEach(aIds -> {
            Authorization authorization = authorizationRepo.findById(request.getAuthorizationId()).get();
            Collection<TemporaryPhoto> authorizedPhotos = authorization.getTemporaryAuthorizedPhotos();
            authorizedPhotos.forEach(photo -> {
                if (request.getAuthorizedPhotos().contains(photo.getId())) {
                    TemporaryAuthorized temporaryAuthorizedPhoto = temporaryAuthorizedRepo.findTopByAuthorizedPhotoIdAndAuthorizationId(photo.getId(), authorization.getId());
                    if (temporaryAuthorizedPhoto != null) {
                        temporaryAuthorizedRepo.deleteById(temporaryAuthorizedPhoto.getId());
                    }
                }
            });
       // });

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping("/list_authorization_for_photo_ids")
    public ResponseEntity<AuthorizationListResponse> listAuthorizationsForPhotoIds(@RequestBody ArrayList<String> photoIds) {

        AuthorizationListResponse response = new AuthorizationListResponse();
        response.setAuthorizationMap(new HashMap<>());

        Iterable<TemporaryPhoto> photos = temporaryPhotoRepo.findAllByIdIn(photoIds);
        photos.forEach(p -> {
            ArrayList<TemporaryAuthorizationDto> dtosForActualPhoto = new ArrayList<>();
            Iterable<Authorization> authorizations = authorizationRepo.findAllByTemporaryAuthorizedPhotos(p);
            authorizations.forEach(a -> {
                TemporaryAuthorizationDto temporaryAuthorizationDto = new TemporaryAuthorizationDto();
                temporaryAuthorizationDto.setAuthorizationKind(a.getAuthorizationKind());
                temporaryAuthorizationDto.setUploadInstant(a.getUploadInstant());
                temporaryAuthorizationDto.setCaption(a.getCaption());
                temporaryAuthorizationDto.setId(a.getId());
                dtosForActualPhoto.add(temporaryAuthorizationDto);
            });
            response.getAuthorizationMap().put(p.getId(), dtosForActualPhoto);
        });


        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/list_authorization_for_user")
    public ResponseEntity<AuthorizationListResponse> listAuthorizationsForUser(@AuthenticationPrincipal UserPrincipal principal) {
        User user = userRepo.findById(principal.getId()).get();
        AuthorizationListResponse response = new AuthorizationListResponse();
        ArrayList<TemporaryAuthorizationDto> responseList = new ArrayList<>();
        Iterable<Authorization> authorizations2 = authorizationRepo.findAllByUserId(user.getId());
        authorizations2.forEach(a -> {

            TemporaryAuthorizationDto actualDto = new TemporaryAuthorizationDto();
            actualDto.setId(a.getId());
            actualDto.setCaption(a.getCaption());
            actualDto.setUploadInstant(a.getUploadInstant());
            actualDto.setAuthorizationKind(a.getAuthorizationKind());
            actualDto.setDocumentUrl(a.getDocumentUrl());
            responseList.add(actualDto);
        });
        response.setAuthorizationList(responseList);

        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }

    private TemporaryAuthorizationDto uploadAndPersistFile(MultipartFile file, User user, String caption, AuthorizationKind authorizationKind) throws IOException {

        String fileName = fileStorageService.uploadMultipartFile(file, FileResolution.FR_);
        // ToDO: external parameter for server Address and pattern
        String serverAddress = "https://picktur.s3.eu-central-1.amazonaws.com/";
        String pattern = "(.*?)amazonaws.com\\/picktur\\/";
        fileName = fileName.replaceAll(pattern, serverAddress);

        Authorization authorization = new Authorization();
        authorization.setAuthorizationKind(authorizationKind);
        authorization.setCaption(caption);
        authorization.setDocumentUrl(fileName);
        authorization.setUploadInstant(Instant.now());
        authorization.setUser(user);
        authorization = authorizationRepo.save(authorization);


        UserAuthorized userAuthorized = new UserAuthorized(user, authorization);
        userAuthorizedRepo.save(userAuthorized);

        TemporaryAuthorizationDto response = new TemporaryAuthorizationDto();
        response.setDocumentUrl(authorization.getDocumentUrl());
        response.setCaption(authorization.getCaption());
        response.setUploadInstant(authorization.getUploadInstant());
        response.setAuthorizationKind(authorization.getAuthorizationKind());
        response.setId(authorization.getId());
        return response;
    }
}
