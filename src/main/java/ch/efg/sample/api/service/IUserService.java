package ch.efg.sample.api.service;

import java.util.List;
import java.util.Map;

/**
 * @author igor
 */
public interface IUserService<T, ID extends String> {

    List<T> findAll();

    List<T> findById(ID id);

    <S extends T> List<S> saveAll(Iterable<S> var1);

    <S extends T> S save(S var1);

    T delete(ID var1);

    /**
     * @return {@code Map} of users by groupId
     */
    Map<String, List<T>> findAllGroupByGroupId();
}
