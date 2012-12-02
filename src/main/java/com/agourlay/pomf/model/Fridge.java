package com.agourlay.pomf.model;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.Serializable;
import java.util.List;

import com.agourlay.pomf.tools.transformer.ExtractFridgeName;
import com.google.common.collect.Lists;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
@Cache
public class Fridge implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6461491688659114651L;
	
	@Id	private String name;
	private FridgeUser owner;	
	
	public Fridge() {}
		
	//DAO METHODS
	public static List<Post> getPosts(String fridgeName) {
        return ofy().load().type(Post.class).filter("fridgeId", fridgeName).limit(100).list();
	}
	
	public static Fridge getFridgeById(String fridgeId){
		return ofy().load().type(Fridge.class).id(fridgeId).get();
	}
	
	public static int countFridge(){
		return ofy().load().type(Fridge.class).count();
	}
	
	public static Fridge createFridge(String fridgeId){
		Fridge newFridge = new Fridge();
		newFridge.setName(fridgeId);
		ofy().save().entity(newFridge).now();
		return newFridge;
	}
	
	public static void createFridgeIfNotExist(String fridgeId) {
        if (getFridgeById(fridgeId) == null) {
        	createFridge(fridgeId);
        }
	}


	public static List<Fridge> searchFridgeLike(String fridgeName){
		return ofy().load().type(Fridge.class).filter("name >=", fridgeName).filter("name <", fridgeName + "\uFFFD").list(); 
	}
	
	public static List<String> searchFridgeNamesWithNameLike(String fridgeName){
		return Lists.transform(searchFridgeLike(fridgeName),  new ExtractFridgeName());
	}
	
	//GETTERS & SETTERS
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FridgeUser getOwner() {
		return owner;
	}

	public void setOwner(FridgeUser owner) {
		this.owner = owner;
	}
}
