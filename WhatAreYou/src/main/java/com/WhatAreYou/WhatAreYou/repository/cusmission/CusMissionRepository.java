package com.WhatAreYou.WhatAreYou.repository.cusmission;

import com.WhatAreYou.WhatAreYou.domain.CusMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CusMissionRepository extends JpaRepository<CusMission, Long> {
}

