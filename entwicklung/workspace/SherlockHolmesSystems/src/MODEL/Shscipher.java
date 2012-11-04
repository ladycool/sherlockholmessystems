package MODEL;

public abstract class Shscipher {
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	
	//Abstract methods
	public abstract Object[] createKey();
	
	public abstract void setKey();
	
	public abstract String encrypt(String text,String key);
	
	//public abstract String encrypt(Object toencript, Object key);
	
	public abstract Object decrypt();
}
