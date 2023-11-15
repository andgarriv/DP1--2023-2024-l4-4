/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package us.l4_4.dp1.end_of_line.authorities;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;
import us.l4_4.dp1.end_of_line.player.Player;

@Service
public class AuthoritiesService {

	private AuthoritiesRepository authoritiesRepository;

	@Autowired
	public AuthoritiesService(AuthoritiesRepository authoritiesRepository) {
		this.authoritiesRepository = authoritiesRepository;
	}

	@Transactional(readOnly = true)
	public Iterable<Authorities> findAll() {
		return this.authoritiesRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Authorities findByAuthority(String authority) {
		return this.authoritiesRepository.findByName(authority)
				.orElseThrow(() -> new ResourceNotFoundException("Authority", "Name", authority));
	}

	@Transactional(readOnly = true)
	public List<Player> findAllByAuthority(String authority) {
		return this.authoritiesRepository.findAllByAuthority(authority);
	}

	@Transactional
	public void saveAuthorities(Authorities authorities) throws DataAccessException {
		authoritiesRepository.save(authorities);
	}

	@Transactional(readOnly = true)
	public Authorities findAuthoritieById(Integer id) {
		return this.authoritiesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Authority", "Id", id));
	}

}
