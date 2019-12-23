package world;

public interface IdGenerator{
	
	 /** 
	  * 
	
	  * @return Integer representing Id which was generated
	  * @throws Exception - when Id cannot be generated
	  */
	 Integer getId() throws Exception;
	 /**
	  * 
	  * @param id - Integer value of Id to be realised
	  */
	 void freeId(Integer id);
	 boolean isOccupied(Integer id);
}
