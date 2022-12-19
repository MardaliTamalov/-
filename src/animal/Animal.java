package animal;

public abstract class Animal{
 public static final Integer VISIBILITY = 2;
 private Integer life;
 private boolean  tired;

 public boolean isTired() {
  return tired;
 }

 public void setTired(boolean tired) {
  this.tired = tired;
 }

 public Integer getLife() {
  return life;
 }

 public Animal() {
  life=3;
   }

 public void starve(){
  life--;
 }

public void eat(){
  life = 3;
 }

}
