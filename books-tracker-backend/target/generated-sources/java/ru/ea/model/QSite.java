package ru.ea.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSite is a Querydsl query type for Site
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSite extends EntityPathBase<Site> {

    private static final long serialVersionUID = -2133763650L;

    public static final QSite site = new QSite("site");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final StringPath name = createString("name");

    public final StringPath url = createString("url");

    public QSite(String variable) {
        super(Site.class, forVariable(variable));
    }

    public QSite(Path<? extends Site> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSite(PathMetadata metadata) {
        super(Site.class, metadata);
    }

}

