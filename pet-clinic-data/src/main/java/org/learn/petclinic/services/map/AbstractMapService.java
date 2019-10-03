package org.learn.petclinic.services.map;

import org.learn.petclinic.model.BaseEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AbstractMapService<T extends BaseEntity,ID extends  Long> {
    protected Map<Long,T> map = new HashMap<>();
    Set<T> findAll(){
        return new HashSet<>(map.values());
    }

    T findById(ID id){
        return map.get(id);
    }

    T save(T object){
        if(object !=null){
            if(object.getId() ==null){
                object.setId(getNextId());
            }
        }else {
            throw  new RuntimeException("Object cannot be null");
        }
        map.put(object.getId(),object);
        return object;
    }

    void deleteById(ID id){
        map.remove(id);
    }
    void delete(T object){
        map.entrySet().removeIf(entry ->entry.getValue().equals(object));
    }
    private Long getNextId(){
        return map.keySet().stream().mapToLong(Long::valueOf).max().orElse(0)+1;
    }
}
