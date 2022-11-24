package com.tcdt.qlnvkhoach.service;

import com.tcdt.qlnvkhoach.repository.catalog.QlnvDmDonviRepository;
import com.tcdt.qlnvkhoach.table.catalog.QlnvDmDonvi;
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

	@Autowired
	QlnvDmDonviRepository qlnvDmDonviRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		QlnvDmDonvi dvi = qlnvDmDonviRepository.findByMaDvi(user.getDvql());
		user.setMaQd(dvi.getMaQd());
		user.setMaTr(dvi.getMaTr());
		user.setMaKhqlh(dvi.getMaKhqlh());
		user.setMaKtbq(dvi.getMaKtbq());
		user.setMaTckt(dvi.getMaTckt());
		user.setCapDvi(dvi.getCapDvi());
		return new CustomUserDetails(user);
	}

	public Iterable<UserAction> findAll() {
		return userActionRepository.findAll();
	}

	public void saveUserHistory(UserHistory userHistory) {
		userHistoryRepository.save(userHistory);
	}

}