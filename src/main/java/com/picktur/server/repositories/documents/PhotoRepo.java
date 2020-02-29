package com.picktur.server.repositories.documents;

import com.arangodb.springframework.repository.ArangoRepository;
import com.picktur.server.entities.Photo;
import com.picktur.server.entities.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface PhotoRepo extends ArangoRepository<Photo, String> {

    Page<Photo> findAllByIdIn(ArrayList<String> idList, Pageable pageable);
    Page<Photo> findAllByPhotoOwnerIdIs(String userId, Pageable pageable);
    //@Cacheable()
    Photo findPhotoById(String string);
    //@Cacheable()
    Page<Photo> findAllByForHome(boolean forHome, Pageable pageable);
    List<Photo> findAllByForHome(boolean forHome);

    //@Cacheable()
    Page<Photo> findAllByForHomeOrderByWeightDesc(boolean forHome, Pageable pageable);
    Page<Photo> findAllByForHomeOrderByUploadInstantDescRatingDesc(boolean forHome, Pageable pageable);

    //@Cacheable()
    Iterable<Photo> findAllByTagsContains(Tag tag);
    Iterable<Photo> findByPhotoCollection_Id(String collectionId);
}
