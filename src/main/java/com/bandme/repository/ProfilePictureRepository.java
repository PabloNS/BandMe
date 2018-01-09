package com.bandme.repository;

import com.bandme.model.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture,Long>{
    ProfilePicture findByFileName(String fileName);
}
