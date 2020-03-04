package br.com.hevermc.hardcoregames.schematicutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Schematic {

	private short[] blocks;
	private byte[] data;
	private short width;
	private short lenght;
	private short height;

	public Schematic(short[] blocks2, byte[] data, short width, short lenght, short height) {
		this.blocks = blocks2;
		this.data = data;
		this.width = width;
		this.lenght = lenght;
		this.height = height;
	}

	public short[] getBlocks() {
		return this.blocks;
	}

	public byte[] getData() {
		return this.data;
	}

	public short getWidth() {
		return this.width;
	}

	public short getLenght() {
		return this.lenght;
	}

	public short getHeight() {
		return this.height;
	}

	public static ArrayList<Block> Circulo = new ArrayList<Block>();
	public static ArrayList<Block> Baus = new ArrayList<Block>();
	public static ArrayList<Block> Enchant = new ArrayList<Block>();
	public static ArrayList<Block> Portoes = new ArrayList<Block>();
	public static ArrayList<Block> Coliseu = new ArrayList<Block>();
	public static ArrayList<Block> Feast = new ArrayList<Block>();
	public static ArrayList<Block> MiniFeast = new ArrayList<Block>();
	public static ArrayList<Block> MFBaus = new ArrayList<Block>();

	@SuppressWarnings("deprecation")
	public static void spawnarSchematic(String tipo, World world, Location loc, Schematic schematic) {
		short[] blocks = schematic.getBlocks();
		byte[] blockData = schematic.getData();

		short length = schematic.getLenght();
		short width = schematic.getWidth();
		short height = schematic.getHeight();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < length; z++) {
					int index = y * width * length + z * width + x;
					Block block = new Location(world, x + loc.getX(), y + loc.getY(), z + loc.getZ()).getBlock();
					block.setTypeIdAndData(blocks[index], blockData[index], true);
					if (tipo.equals("coliseu")) {
						if (block.getType() != Material.FENCE && block.getType() != Material.FENCE
								&& block.getType() != Material.PISTON_BASE && block.getType() != Material.AIR) {
							Coliseu.add(block);
						}
						if ((block.getType().equals(Material.FENCE)) || (block.getType().equals(Material.FENCE)
								|| (block.getType().equals(Material.PISTON_BASE)))) {
							Portoes.add(block);
						}
					} else if (tipo.equals("feast")) {
						if (block.getType() == Material.CHEST) {
							Baus.add(block);
						}
						if (block.getType() == Material.ENCHANTMENT_TABLE) {
							Enchant.add(block);
						} else if (block != null) {
							Feast.add(block);
						}
					} else if (tipo.equals("minifeast")) {
						if (block.getType() == Material.CHEST) {
							MFBaus.add(block);
						}
						if (block != null) {
							MiniFeast.add(block);
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("resource")
	public static Schematic carregarSchematic(File file) throws IOException {
		FileInputStream stream = new FileInputStream(file);
		NBTInputStream nbtStream = new NBTInputStream(stream);

		CompoundTag schematicTag = (CompoundTag) nbtStream.readTag();

		if (!schematicTag.getName().equals("Schematic"))
			throw new IllegalArgumentException("Tag \"Schematic\" does not exist or is not first");

		Map<String, Tag> schematic = schematicTag.getValue();
		if (!schematic.containsKey("Blocks"))
			throw new IllegalArgumentException("Schematic file is missing a \"Blocks\" tag");

		short width = ((ShortTag) getChildTag(schematic, "Width", ShortTag.class)).getValue().shortValue();
		short length = ((ShortTag) getChildTag(schematic, "Length", ShortTag.class)).getValue().shortValue();
		short height = ((ShortTag) getChildTag(schematic, "Height", ShortTag.class)).getValue().shortValue();

		byte[] blockId = ((ByteArrayTag) getChildTag(schematic, "Blocks", ByteArrayTag.class)).getValue();
		byte[] blockData = ((ByteArrayTag) getChildTag(schematic, "Data", ByteArrayTag.class)).getValue();
		byte[] addId = new byte[0];
		short[] blocks = new short[blockId.length];
		if (schematic.containsKey("AddBlocks"))
			addId = ((ByteArrayTag) getChildTag(schematic, "AddBlocks", ByteArrayTag.class)).getValue();

		for (int index = 0; index < blockId.length; index++)
			if (index >> 1 >= addId.length) {
				blocks[index] = ((short) (blockId[index] & 0xFF));
			} else if ((index & 0x1) == 0) {
				blocks[index] = ((short) (((addId[(index >> 1)] & 0xF) << 8) + (blockId[index] & 0xFF)));
			} else {
				blocks[index] = ((short) (((addId[(index >> 1)] & 0xF0) << 4) + (blockId[index] & 0xFF)));
			}

		return new Schematic(blocks, blockData, width, length, height);
	}

	private static <T extends Tag> T getChildTag(Map<String, Tag> items, String key, Class<T> expected)
			throws IllegalArgumentException {
		if (!items.containsKey(key))
			throw new IllegalArgumentException("Schematic file is missing a \"" + key + "\" tag");

		Tag tag = (Tag) items.get(key);
		if (!expected.isInstance(tag))
			throw new IllegalArgumentException(key + " tag is not of tag type " + expected.getName());

		return expected.cast(tag);
	}
}
