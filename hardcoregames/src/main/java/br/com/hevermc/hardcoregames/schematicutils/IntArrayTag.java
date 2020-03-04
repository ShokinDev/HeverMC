package br.com.hevermc.hardcoregames.schematicutils;

public final class IntArrayTag
  extends Tag
{
  private final int[] value;
  
  public IntArrayTag(String name, int[] value)
  {
    super(name);
    this.value = value;
  }
  
  public int[] getValue()
  {
    return this.value;
  }
  
  public String toString()
  {
    StringBuilder hex = new StringBuilder();
    int[] arrayOfInt;
    int j = (arrayOfInt = this.value).length;
    for (int i = 0; i < j; i++)
    {
      int b = arrayOfInt[i];
      String hexDigits = Integer.toHexString(b).toUpperCase();
      if (hexDigits.length() == 1) {
        hex.append("0");
      }
      hex.append(hexDigits).append(" ");
    }
    String name = getName();
    String append = "";
    if ((name != null) && (!name.equals(""))) {
      append = "(\"" + getName() + "\")";
    }
    return "TAG_Int_Array" + append + ": " + hex.toString();
  }
}
