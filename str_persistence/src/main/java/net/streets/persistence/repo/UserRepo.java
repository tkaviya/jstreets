package net.streets.persistence.repo;

/***************************************************************************
 *                                                                         *
 * Created:     21 / 09 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.streets.persistence.entity.complex_type.str_user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<str_user, Long> {}