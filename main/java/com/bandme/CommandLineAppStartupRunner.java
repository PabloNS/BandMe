package com.bandme;

import com.bandme.model.*;
import com.bandme.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

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

    @Autowired
    private MessageService messageService;

    @Resource
    private StorageService storageService;

    @Override
    @Transactional
    public void run(String...args) throws Exception {
        storageService.deleteAll();
        storageService.init();

        File source = new File("src//main//resources//static//images//defaultProfPicture.png");
        String base64Image = "";
        try (FileInputStream imageInFile = new FileInputStream(source)) {
            // Reading a Image file from file system
            byte imageData[] = new byte[(int) source.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }

        /*File source = new File("src//main//resources//static//images//defaultProfPicture.png");
        File dest = new File("src//main/resources//static//images//profilePictures//defaultProfPicture.png");
        FileCopyUtils.copy(source,dest);*/

        Band band = bandService.findByName("Green Day");
        Band band2 = bandService.findByName("Ramones");
        Band band3 = bandService.findByName("blink-182");

        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("12345");
        user.setName("Test");
        user.setLastName("User");
        user.setNickName("TEST1");
        user.setFavouriteBands(Arrays.asList(band,band2,band3));
        user.setImageBytes(base64Image);
        userService.registerUser(user);

        User user2 = new User();
        user2.setEmail("email@email.com");
        user2.setPassword("12345");
        user2.setName("Test2");
        user2.setLastName("User2");
        user2.setNickName("TEST2");
        user2.setFavouriteBands(Arrays.asList(band,band2,band3));
        user2.setImageBytes(base64Image);
        userService.registerUser(user2);

        Message m = new Message();
        m.setFromUser(user2);
        m.setToUser(user);
        m.setDate(new Date());
        m.setContent("blablabla");
        messageService.saveMessage(m);

        Message m2 = new Message();
        m2.setFromUser(user2);
        m2.setToUser(user);
        m2.setDate(new Date());
        m2.setContent("blablabla2");
        m2.setReadMessage(true);
        messageService.saveMessage(m2);

        for(int i=0;i<50;i++){
            Post post = new Post();
            post.setUser(user);
            post.setDescription("Hello there " + i);
            post.setInfluenceBands(Arrays.asList(bandService.findByName("Ramones"), bandService.findByName("Green Day"), bandService.findByName("blink-182")));
            post.setDate(new Date());
            if(i%2==0){
                post.setInstrument(instrumentService.findByName("Electric Guitar"));
            } else if(i%3==0){
                post.setInstrument(instrumentService.findByName("Bass"));
            } else {
                post.setInstrument(instrumentService.findByName("Drums"));
            }
            postService.savePost(post);
        }

    }
}
