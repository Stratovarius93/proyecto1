package com.aristos.proyecto1.repositories;

import com.aristos.proyecto1.documents.Element;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface ElementRepository extends ReactiveSortingRepository<Element, Long> {
}
