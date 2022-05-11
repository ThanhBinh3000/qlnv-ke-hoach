package com.tcdt.qlnvkhoach.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tcdt.qlnvkhoach.jwt.CustomUserDetails;
import com.tcdt.qlnvkhoach.repository.UserActionRepository;
import com.tcdt.qlnvkhoach.repository.UserHistoryRepository;
import com.tcdt.qlnvkhoach.repository.UserInfoRepository;
import com.tcdt.qlnvkhoach.table.UserAction;
import com.tcdt.qlnvkhoach.table.UserHistory;
import com.tcdt.qlnvkhoach.table.UserInfo;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserInfoRepository userRepository;
	@Autowired
	UserHistoryRepository userHistoryRepository;

	@Autowired
	UserActionRepository userActionRepository;

	@Autowired
	QlnvDmService qlnvDmService;

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