package ru.sberbank.sberbank.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@Slf4j
public class GeneralRestController
            <
                T_controller extends GeneralRestControllerMethods<T_entity>,
                T_service extends GeneralService<T_entity>,
                T_entity extends AbstractGeneralParentEntity<T_entity>
            >
        implements GeneralRestControllerMethods<T_entity>
{

    private final T_service m_iTservice;
    private final Class<T_controller> entityClass;

    protected Link getAllLink = null;
    protected Link getByIdLink = null;
    protected Link createEntityLink = null;
    protected Link patchEntityLink = null;
    protected Link putEntityLink = null;
    protected Link deleteEntityLink = null;

    public ResponseEntity<CollectionModel<T_entity>> getAll(HttpServletRequest request){
        List<T_entity> entities = m_iTservice.findAll();
        CollectionModel<T_entity> model = CollectionModel.of(entities);
        getAllLink = new Link(linkTo(methodOn(entityClass).getAll(request)).withSelfRel().toUri().getPath(),"self");
        model.add(getAllLink);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }


    public ResponseEntity<T_entity> getById(Integer id, HttpServletRequest request){
        try
        {
            Optional<T_entity> optEntity = m_iTservice.findById(id);

            if(!optEntity.isPresent()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            optEntity.get().add(createLinks(id, request, optEntity.get(), "getById"));

            return new ResponseEntity<>(optEntity.get(), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


    public ResponseEntity<T_entity> create(T_entity dto, HttpServletRequest request) {

        T_entity tEntity = null;

        try{
            tEntity = m_iTservice.add(dto);
            Integer id = tEntity.getId();
            if(id != null){
                tEntity.add(createLinks(id, request, tEntity, "create"));
            }
            return new ResponseEntity<>(tEntity, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    public static void copyNonNullProperties(Object src, Object target){
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static String[] getNullPropertyNames(Object source){
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds){
            Object srcValue = src.getPropertyValue(pd.getName());
            if(srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


    public ResponseEntity<Object> doPatch(Integer id, T_entity new_entity,HttpServletRequest request) {

        Optional<T_entity> optEntity = m_iTservice.findById(id);

        try{
            if(!optEntity.isPresent()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            copyNonNullProperties(new_entity, optEntity.get());
            if(id != null){
                optEntity.get().add(createLinks(id, request, optEntity.get(), "patch"));
            }
            T_entity entity = m_iTservice.edit(optEntity.get());
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<Object> doPut (Integer id, T_entity new_entity,HttpServletRequest request) {
        Optional<T_entity> optEntity = m_iTservice.findById(id);
        try{
            if(!optEntity.isPresent()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            if(new_entity.getId() != null && !optEntity.get().getId().equals(new_entity.getId()))
            {
                new_entity.setId(id);
            }

            T_entity entity = m_iTservice.edit(new_entity);

            if(id != null){
                entity.add(createLinks(id, request, optEntity.get(), "put"));
            }
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }



    public ResponseEntity<Object> doDelete(Integer id,HttpServletRequest request) {
        Optional<T_entity> optEntity = m_iTservice.findById(id);

        try {

            if (!optEntity.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            m_iTservice.delete(optEntity.get());

            if(id != null){
                optEntity.get().add(createLinks(id, request, optEntity.get(),"delete"));
            }

            return new ResponseEntity<>(optEntity.get(), HttpStatus.OK);

        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


    protected Links createLinks(Integer id, HttpServletRequest request, T_entity entity, String strCurrentRel) throws Exception {
        EntityModel<Object> model = new EntityModel<>(new Object());

        getAllLink = new Link(linkTo(methodOn(entityClass).getAll(request)).toUri().getPath(),"getAll".equals(strCurrentRel) ? "self" : "getAll");
        getByIdLink = new Link(linkTo(methodOn(entityClass,id).getById(id, request)).toUri().getPath(),"getById".equals(strCurrentRel) ? "self" : "getById");
        createEntityLink = new Link(linkTo(methodOn(entityClass).create(entity, request)).toUri().getPath(),"create".equals(strCurrentRel) ? "self" : "create");
        patchEntityLink = new Link(linkTo(methodOn(entityClass,id).doPatch(id, entity, request)).toUri().getPath(),"patch".equals(strCurrentRel) ? "self" : "patch");
        putEntityLink = new Link(linkTo(methodOn(entityClass,id).doPut(id, entity, request)).toUri().getPath(),"put".equals(strCurrentRel) ? "self" : "put");
        deleteEntityLink = new Link(linkTo(methodOn(entityClass,id).doDelete(id, request)).toUri().getPath(),"delete".equals(strCurrentRel) ? "self" : "delete");


        model.add(getAllLink,getByIdLink,createEntityLink,patchEntityLink,putEntityLink,deleteEntityLink);
        return model.getLinks();
    }
}
