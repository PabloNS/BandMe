package com.bandme;

import com.bandme.model.*;
import com.bandme.repository.ProfilePictureRepository;
import com.bandme.service.*;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private InstrumentService instrumentService;

    @Autowired
    private BandService bandService;

    @Autowired
    private PostService postService;

    @Resource
    StorageService storageService;

    @Autowired
    ProfilePictureRepository pictureRepository;

    @Override
    @Transactional
    public void run(String...args) throws Exception {
        storageService.deleteAll();
        storageService.init();


        File source = new File("src//main//resources//static//images//defaultProfPicture.png");
        File dest = new File("src//main/resources//static//images//profilePictures//defaultProfPicture.png");
        FileCopyUtils.copy(source,dest);

        Band band = bandService.findByName("Green Day");
        Band band2 = bandService.findByName("Ramones");
        Band band3 = bandService.findByName("blink-182");

        ProfilePicture defaultProfPicture = new ProfilePicture();
        defaultProfPicture.setFileName("defaultProfPicture.png");
        pictureRepository.save(defaultProfPicture);

        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("12345");
        user.setName("Peter");
        user.setLastName("Parker");
        user.setFavouriteBands(Arrays.asList(band,band2,band3));
        user.setProfilePicture(defaultProfPicture);
        userService.registerUser(user);

        Post post = new Post();
        post.setUser(user);
        post.setDescription("Hello there");
        post.setInfluenceBands(Arrays.asList(bandService.findByName("Ramines"), bandService.findByName("Green Day"), bandService.findByName("blink-182")));
        post.setInstrument(instrumentService.findByName("Guitar"));
        postService.savePost(post);
    }
}
