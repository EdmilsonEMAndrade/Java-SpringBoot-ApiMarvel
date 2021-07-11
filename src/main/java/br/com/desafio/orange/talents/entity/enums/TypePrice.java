package br.com.desafio.orange.talents.entity.enums;

public enum TypePrice {
		PRINT (1,"printPrice"),
		DIGITAL (2, "digitalPurchasePrice"),
		OTHER (3, "other");
		
		private Integer cod;
		private String status;
		
		private TypePrice(Integer cod, String status) {
			this.cod = cod;
			this.status = status;
		}

		public Integer getCod() {
			return cod;
		}

		public String getStatus() {
			return status;
		}
		
		public static TypePrice toEnum(Integer cod) {
			for(TypePrice x : TypePrice.values()){
				if(cod.equals(x.getCod())) {
					return x;
				}
			}
			throw new IllegalArgumentException("Invalid code: " + cod );
		}
		
		public static TypePrice toEnumByString(String status) {
			for(TypePrice x : TypePrice.values()){
				if(status.equals(x.getStatus())) {
					return x;
				}
			}
			return TypePrice.OTHER;			
		}

}
