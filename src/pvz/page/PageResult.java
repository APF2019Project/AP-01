package pvz.page;

public class PageResult<U> {
  public final boolean isAborted;
  public final U result;

  public PageResult(boolean isAborted, U result) {
    this.isAborted = isAborted;
    this.result = result;
  }
  
  public static <X> PageResult<X> aborted(){
    return new PageResult<>(true, null);
  }
}