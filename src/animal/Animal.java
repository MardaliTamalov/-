package animal;

public abstract class Animal{
 public static final Integer VISIBILITY = 2;
 private Integer life;
 private boolean  satiety;

 public Integer getLife() {
  return life;
 }

 public Animal() {
  life=3;
  satiety = false;
 }

 public void starve(){
  life--;
 }

public void eat(){
  life = 3;
 }

}
