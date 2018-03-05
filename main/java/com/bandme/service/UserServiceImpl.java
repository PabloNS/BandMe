package com.bandme.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bandme.model.Role;
import com.bandme.model.User;
import com.bandme.repository.RoleRepository;
import com.bandme.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User findUserById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findUserByNickName(String nickName)  {
		return userRepository.findByNickName(nickName);
	}

	@Override
	public void saveUser(User user) {
		/*if(user.getImageBytes()==null || user.getImageBytes().isEmpty()){
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
			user.setImageBytes(base64Image);
		}*/
		userRepository.save(user);
	}

	@Override
	public void registerUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		Role userRole = roleRepository.findByRole("USER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		if(user.getImageBytes()==null || user.getImageBytes().isEmpty()){
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
			user.setImageBytes(base64Image);
		}
		userRepository.save(user);
	}

	public void saveAdmin(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Role userRole = roleRepository.findByRole("ADMIN");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	public void saveRole(Role role){
		roleRepository.save(role);
	}


	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public RoleRepository getRoleRepository() {
		return roleRepository;
	}

	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public BCryptPasswordEncoder getbCryptPasswordEncoder() {
		return bCryptPasswordEncoder;
	}

	public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
}
