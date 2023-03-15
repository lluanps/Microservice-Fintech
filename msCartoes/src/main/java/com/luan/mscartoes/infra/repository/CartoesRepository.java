package com.luan.mscartoes.infra.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luan.mscartoes.domain.Cartao;

@Repository
public interface CartoesRepository extends JpaRepository<Cartao, Long>{

	List<Cartao> findByRendaLessThanEqual(BigDecimal renda);

}
