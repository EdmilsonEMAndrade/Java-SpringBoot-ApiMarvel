package br.com.desafio.orange.talents.entity.DTO;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import br.com.desafio.orange.talents.entity.Comic;
import br.com.desafio.orange.talents.entity.Prices;


public class ComicListDTO extends ComicDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String isbn;
	private String discount;
	private Set<Prices> prices = new HashSet<>();
	
	public ComicListDTO(Comic comic) {
		super(comic);
		this.isbn = comic.getIsbn();
		this.prices = comic.getPrices();
		setDiscount();
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}			
	
	public Set<Prices> getPrices() {
		return prices;
	}

	public void setPrices(Set<Prices> prices) {
		this.prices = prices;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount() {
		if(!isbn.isBlank()) {
			String lastChar = isbn.substring(isbn.length()-1);			
			int number = Integer.parseInt(lastChar);			
			if(number%2==1) number -= 1 ;
			System.out.println(number);
				switch(number) {
				  case 0:
					  discount = returnDiscountMessage("MONDAY");
					  break;
				  case 2:
					  discount = returnDiscountMessage("TUESDAY");
					  break;
				  case 4:
					  discount = returnDiscountMessage("WEDNESDAY");
					  break;
				  case 6:
					  discount = returnDiscountMessage("THURSDAY");
					  break;
				  case 8:
					  discount = returnDiscountMessage("FRIDAY");
					  break;
				}
		}
		else {			
			discount = "This comic is not in the items that can be discounted, as it does not have an ISBN code.";
		}		
	}
	
	private String returnDiscountMessage(String todayDayWeek){
		LocalDate calendario = LocalDate.now();
		DayOfWeek day = calendario.getDayOfWeek();
		String weekDay = day.name();
	    if(weekDay.equals(todayDayWeek)) {
	    	for(Prices price:prices) {
	    		price.setPrice(price.getPrice()*.9);
	    	}
	    	return String.format("You are lucky. Today is %s, and the comic code ISBN %s is discounted. Updated price with 10 per cent off", weekDay, isbn);
	    }else {
	    	return String.format("Today is %s and according to the ISBN code %s of the comic the discount is on %s",weekDay, isbn, todayDayWeek);
	    }
	}
	
}
