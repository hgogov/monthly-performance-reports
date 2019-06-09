package monthlyreports;

public class Employee {

	private String name;
	private int totalSales;
	private int salesPeriod;
	private float experienceMultiplier;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(int totalSales) {
		this.totalSales = totalSales;
	}

	public int getSalesPeriod() {
		return salesPeriod;
	}

	public void setSalesPeriod(int salesPeriod) {
		this.salesPeriod = salesPeriod;
	}

	public float getExperienceMultiplier() {
		return experienceMultiplier;
	}

	public void setExperienceMultiplier(Float experienceMultiplier) {
		this.experienceMultiplier = experienceMultiplier;
	}
}
