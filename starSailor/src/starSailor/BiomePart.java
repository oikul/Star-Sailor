package starSailor;

public class BiomePart {
	
	private Block block;
	private double start, end, chance;
	
	public BiomePart(Block block, double start, double end, double chance){
		this.setBlock(block);
		this.setStart(start);
		this.setEnd(end);
		this.setChance(chance);
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public double getStart() {
		return start;
	}

	public void setStart(double start) {
		this.start = start;
	}

	public double getEnd() {
		return end;
	}

	public void setEnd(double end) {
		this.end = end;
	}

	public double getChance() {
		return chance;
	}

	public void setChance(double chance) {
		this.chance = chance;
	}

}
