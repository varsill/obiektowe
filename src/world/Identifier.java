package world;

public class Identifier extends Object {
	
	private Integer idNumber;
	private final IdGenerator generator;
	//Constructors:
	/**
	 * disabled constructor
	 */
	@SuppressWarnings("unused")
	private Identifier()
	{
		idNumber=-1;
		generator=null;
	} 
	/**
	 * Constructor for obtaining new identifier
	 * If  generator cannot generate numerical id then id_num is set to -1.
	 * @param generator - source capable of creating numerical id
	 * 
	 */
	public Identifier(IdGenerator generator)
	{
		this.generator=generator;
		Integer id;
		try
		{
			id = generator.getId();
		}
		catch(Exception e)
		{
			this.idNumber=-1;
			return;//couldn't create id with given generator
		}
		
		this.idNumber=id;
		
	}
	/**
	 * Constructor for reusing already taken ids (for example during deserialization)
	 * @param generator - source capable of creating numerical id 
	 * @param x - integer representing the id we want to reuse
	 * @throws Exception - thrown if proposed id is already in use and user cannot retake it
	 * 
	 */
	public Identifier(IdGenerator generator, int x) throws Exception
	{
		this.generator=generator;
		if(!generator.isOccupied(x))this.idNumber=x;
		else throw new Exception("Proposed id is already in use.");
	}
	
	
	//Methods:
	/**
	 * If id_num is not set (for example due to generator failure) then function will return null value
	 * @return numerical representation of id. 
	 */
	public Integer readAsInteger()
	{
		if(this.idNumber.equals(-1))//id_num hasn't been set
		{
			return null;
		}
		else return new Integer(this.idNumber);//returning a copy because we don't want to have id_num unauthorizedly modified
	}
	
	
	/**
	 * releases id in generator linked with this id
	 */
	public void release()
	{
		generator.freeId(this.idNumber);
		this.idNumber=new Integer(-1);
	}
	
	@Override
	public void finalize()
	{
		release();
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(this==other)
			return true;
		
		if(other instanceof Identifier)
		{		
			if(this.generator.equals( ( (Identifier) other).generator ) && this.idNumber.equals(((Identifier) other).idNumber) )
				return true;	
		}
		
		else if(other instanceof Integer)
		{
			if(this.idNumber.equals(other))
				return true;	
		}
		
		return false;
	}
	


	
}
