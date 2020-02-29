package com.picktur.server.controllers.user_actions;

import com.picktur.server.services.instagram_checks.InstagramLogin;
import com.picktur.server.services.instagram_checks.InstagramUser;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user_social_actions")
public class InstagramController {

    @Autowired
    InstagramLogin instagramLogin;

    @Autowired
    InstagramUser instagramUser;

    @GetMapping("/ig_user_details/{name}")
   public String printInstagramFollowerNumber(@PathVariable(value="name") String name) throws IOException {
        Instagram4j instagram = instagramLogin.getInstagram();
       StringBuilder sb = new StringBuilder();
       String lineSeparator = "\n";

        // Get User to scrap

        InstagramSearchUsernameResult userResult = instagramUser.userResult(name);
        sb.append("ID for ").append(name) .append("is").append(  userResult.getUser().getPk()).append(lineSeparator);
        sb.append("Number of followers: ").append( userResult.getUser().getFollower_count()).append(lineSeparator);

        // Get Followers of user

        InstagramGetUserFollowersResult followerList = instagram.sendRequest(new InstagramGetUserFollowersRequest(userResult.getUser().getPk()));
        List<InstagramUserSummary> users = followerList.getUsers();
        sb.append(lineSeparator);
        sb.append("Followers:").append(lineSeparator);
        for (InstagramUserSummary user : users) {
            sb.append("User ").append( user.getUsername()).append(";").append(lineSeparator);
        }
        return sb.toString();
   }

    @GetMapping("/is_user_follower/{account}/{follower}")
    public boolean checkFollower(@PathVariable(value="account") String account, @PathVariable(value="follower") String follower) throws IOException {
        Instagram4j instagram = instagramLogin.getInstagram();
        InstagramSearchUsernameResult accountToFollow = instagramUser.userResult(account);
        InstagramSearchUsernameResult accountThatFollows = instagramUser.userResult(follower);
        InstagramGetUserFollowersResult followerList = instagram.sendRequest(new InstagramGetUserFollowersRequest(accountToFollow.getUser().getPk()));


        List <InstagramUserSummary> followerDetailList = followerList.getUsers();
        for (InstagramUserSummary user : followerDetailList){
            if (user.username.equals(accountThatFollows.getUser().username)){
                return true;
            }
        }

        return false;
    }

}
