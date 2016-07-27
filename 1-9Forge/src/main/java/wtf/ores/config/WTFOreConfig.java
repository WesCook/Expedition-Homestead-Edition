package wtf.ores.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class WTFOreConfig {

	public static boolean cancelVanillaCoal;
	public static boolean cancelVanillaIron;
	public static boolean cancelVanillaGold;
	public static boolean cancelVanillaLapis;
	public static boolean cancelVanillaRedstone;
	public static boolean cancelVanillaQuartz;
	public static boolean cancelVanillaEmerald;
	public static boolean cancelVanillaDiamond;
	public static boolean genNitreOre;
	
	public static boolean simplexGen;
	
	public static void loadConfig() throws Exception {

		Configuration config = new Configuration(new File("config/WTFOresConfig.cfg"));

		config.load();
		
		simplexGen = config.get("Gen Options", "Use simplex noise instead of random for ore generation", true).getBoolean();
		
		String vanilla = "Generation of Vanilla Ore";
		String cancel = "Catch and cancel vanilla ore generation, and use custom generation instead";
		
		cancelVanillaCoal = config.get(cancel, "Coal", true).getBoolean();
		String coal = config.get(vanilla, "Vanilla Coal Generation", "vein, minecraft:coal_ore@0, orePerChunk=60 & 220, GenHeightPercentSurface=20 & 120,"
				+ " VeinDimensions=8 & 8 & 1, pitch=1.5, genPercentInBiomeType=swamp@150, genPercentInBiomeType=hot@50").getString();
		if (cancelVanillaCoal){
			ParseOre.parse(coal);
		}
		
		cancelVanillaIron = config.get(cancel, "Iron", true).getBoolean();
		String iron = config.get(vanilla, "Vanilla Iron Generation", "vein, minecraft:iron_ore@0, orePerChunk=30 & 120, GenHeightPercentSurface=10 & 105, VeinDimensions=8 & 2 & 3,"
				+ " DensityPercent=80, pitch=1.5, genPercentInBiomeType=mountain@150, genPercentInBiomeType=savanna@50 ").getString();
		if (cancelVanillaIron){
			ParseOre.parse(iron);
		}
		
		cancelVanillaGold = config.get(cancel, "Gold", true).getBoolean();
		String gold = config.get(vanilla, "Vanilla Gold Generation", "cloud, minecraft:gold_ore@0, orePerChunk=-12 & 20, GenHeightPercentSurface=5 & 45, size=10, DensityPercent=10,"
				+ " genPercentInBiomeType=river@150, genPercentInBiomeType=forest@50").getString();
		if (cancelVanillaGold){
			ParseOre.parse(gold);
		}
		
		cancelVanillaLapis = config.get(cancel, "Lapis", true).getBoolean();
		String lapis = config.get(vanilla, "Vanilla Lapis Generation", "cluster, minecraft:lapis_ore@0, orePerChunk=1 & 5, GenHeightPercentSurface=1 & 50, "
				+ "genPercentInBiomeType=ocean@150, genPercentInBiomeType=beach@150, genPercentInBiomeType=dry@50").getString();
		if (cancelVanillaLapis){
			ParseOre.parse(lapis);
		}
		
		cancelVanillaRedstone = config.get(cancel, "Redstone", true).getBoolean();
		String redstone = config.get(vanilla, "Vanilla Redstone Generation", "vein, minecraft:redstone_ore@0, orePerChunk=10 & 38, GenHeightPercentSurface=5 & 60, VeinDimensions=8 & 1 & 1,"
				+ " pitch=0, genPercentInBiomeType=sandy@150, genPercentInBiomeType=wet@50").getString();
		if (cancelVanillaRedstone){			
			ParseOre.parse(redstone);
		}
	
		
		cancelVanillaEmerald = config.get(cancel, "Emerald", true).getBoolean();
		String emerald = config.get(vanilla, "Vanilla Emerald Generation", "single, minecraft:emerald_ore@0, orePerChunk=-10 & 10, GenHeightPercentSurface=1 & 33,"
				+ " genPercentInBiomeType=hills@150, genPercentInBiomeType=wet@50").getString();
		if (cancelVanillaEmerald){
			ParseOre.parse(emerald);
		}
		
		cancelVanillaDiamond = config.get(cancel, "Diamond", true).getBoolean();
		String diamond = config.get(vanilla, "Vanilla Diamond Generation", "cluster, minecraft:diamond_ore@0, orePerChunk=-17 & 23, GenHeightPercentSurface=1 & 25,"
				+ " DensityPercent=50, genPercentInBiomeType=jungle@150, genPercentInBiomeType=swamp@50").getString();
		if (cancelVanillaDiamond){
			ParseOre.parse(diamond);
		}
		
		//if (cancelVanillaQuartz = config.get(cancel, "Quartz", true).getBoolean()){
		//	ParseOre.parse(config.get(vanilla, "Vanilla Quartz Generation", "vanilla, minecraft:quartz_ore@0, orePerChunk=60 & 220, GenHeightPercentSurface=20 & 120, size=16, dimension=-1").getString());
		//}
		
		String modOre = "Mod Added Ores Generation Config strings";
		String enableMod = "Mod Added Ores : Enable";
		
		genNitreOre = config.get(enableMod, "Mod Added Ore # 1", true).getBoolean();
		String nitre = config.get(modOre, "# 1", "cave, wtfcore:oreNitre@0, orePerChunk=5 & 10, GenHeightPercentSurface=15 & 95").getString();
		if (genNitreOre){
			ParseOre.parse(nitre);
		}
		
		int extralines = config.get("Mod Added Ores", "Generate extra lines to add custom ores, requires restarting minecraft to generate ", 1).getInt()-1;
		
		for (int loop = 2; loop < extralines+2; loop++){
			Boolean gen = config.get(enableMod, "Mod Added Ore # " + loop, false).getBoolean();
			String ore = config.get(modOre, "# " + loop, "GenerationType, ModID:RegistryName@metadata, orePerChunk= min & max, GenHeightPercentSurface= min & max").getString();
			if (gen) {
				ParseOre.parse(ore);
			}
		}
		
		config.save();
	}
}