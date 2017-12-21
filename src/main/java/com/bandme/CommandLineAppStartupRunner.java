package com.bandme;

import com.bandme.model.*;
import com.bandme.service.BandService;
import com.bandme.service.InstrumentService;
import com.bandme.service.PostService;
import com.bandme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
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

    @Override
    @Transactional
    public void run(String...args) throws Exception {
        Band band = bandService.findByName("Green Day");
        Band band2 = bandService.findByName("Ramones");
        Band band3 = bandService.findByName("blink-182");

        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("12345");
        user.setName("Peter");
        user.setLastName("Parker");
        user.setFavouriteBands(Arrays.asList(band,band2,band3));
        userService.registerUser(user);

        Post post = new Post();
        post.setUser(user);
        post.setDescription("Hello there");
        post.setInfluenceBands(Arrays.asList(bandService.findByName("Ramines"), bandService.findByName("Green Day"), bandService.findByName("blink-182")));
        post.setInstrument(instrumentService.findByName("Guitar"));
        postService.savePost(post);
    }
}
