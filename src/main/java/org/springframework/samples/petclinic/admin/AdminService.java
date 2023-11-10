package org.springframework.samples.petclinic.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;


@Service
public class AdminService {
    
    private AdminRepository adminRepository;

	@Autowired
	public AdminService(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	@Transactional
	public Admin createAdmin(Admin admin){
		return adminRepository.save(admin);
	}

}
