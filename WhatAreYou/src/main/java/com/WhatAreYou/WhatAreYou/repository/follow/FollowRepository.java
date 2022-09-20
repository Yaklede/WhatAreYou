package com.WhatAreYou.WhatAreYou.repository.follow;

import com.WhatAreYou.WhatAreYou.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Long>,CustomFollowRepository {

}
