package ru.sberbank.sberbank.util;

import org.springframework.hateoas.RepresentationModel;

public abstract class AbstractGeneralParentEntity<T extends RepresentationModel<? extends T>> extends RepresentationModel<T> implements GeneralEntity<T> {
}
