package com.tcdt.qlnvkhoachphi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tcdt.qlnvkhoachphi.jwt.CustomUserDetails;
import com.tcdt.qlnvkhoachphi.repository.UserActionRepository;
import com.tcdt.qlnvkhoachphi.repository.UserHistoryRepository;
import com.tcdt.qlnvkhoachphi.repository.UserInfoRepository;
import com.tcdt.qlnvkhoachphi.table.UserAction;
import com.tcdt.qlnvkhoachphi.table.UserHistory;
import com.tcdt.qlnvkhoachphi.table.UserInfo;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserInfoRepository userRepository;
	@Autowired
	UserHistoryRepository userHistoryRepository;

	@Autowired
	UserActionRepository userActionRepository;

	@Autowired
	QlnvDmDonViService donViService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		// TODO:cache thong tin don vi cua user dang nhap
//		QlnvDmDonvi abc = donViService.getDonViById(user.getDvql());
//		System.out.println(abc);
		return new CustomUserDetails(user);
	}

	public Iterable<UserAction> findAll() {
		return userActionRepository.findAll();
	}

	public void saveUserHistory(UserHistory userHistory) {
		userHistoryRepository.save(userHistory);
	}

}