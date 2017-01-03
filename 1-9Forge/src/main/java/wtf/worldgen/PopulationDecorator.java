package wtf.worldgen;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wtf.api.PopulationGenerator;
import wtf.config.CoreConfig;
import wtf.config.OverworldGenConfig;
import wtf.utilities.wrappers.AdjPos;
import wtf.utilities.wrappers.CaveListWrapper;
import wtf.utilities.wrappers.CavePosition;
import wtf.utilities.wrappers.ChunkCoords;
import wtf.utilities.wrappers.ChunkScan;
import wtf.utilities.wrappers.SurfacePos;
import wtf.worldgen.caves.CaveBiomeGenMethods;
import wtf.worldgen.caves.CaveProfile;
import wtf.worldgen.caves.CaveTypeRegister;

public class PopulationDecorator extends PopulationGenerator{
	
	@Override
	public void generate(World world, ChunkCoords coords, Random random, ChunkScan chunkscan) throws Exception {
		
		CaveBiomeGenMethods gen = new CaveBiomeGenMethods(world, coords, random);
		if (CoreConfig.caveGeneration){
			genCaves(world, gen, coords, random, chunkscan);
		}
		//get cave profile for a block roughlyh in the center of the chunk's surface

		//the tree populator has it's own setblockset, so we run setblockset now
		gen.blocksToSet.setBlockSet();
	}
	
	public static void genCaves(World world, CaveBiomeGenMethods gen, ChunkCoords coords, Random random, ChunkScan chunkscan){
		for (CaveListWrapper cave : chunkscan.caveset){

			CaveProfile profile = CaveTypeRegister.getCaveProfile(cave.getBiome(world));
			double depth =  (float)cave.getAvgFloor()/chunkscan.surfaceAvg;
			AbstractCaveType cavetype = profile.getCave(cave.cave.get(0), (int) chunkscan.surfaceAvg);

			CavePosition position;
			Iterator<BlockPos> wallIterator = cave.wall.iterator();
			BlockPos pos;
			while (wallIterator.hasNext()){
				pos = wallIterator.next();
				int x = pos.getX() >> 4;
				int z = pos.getX() >> 4;
				if (x != coords.getChunkX() || z != coords.getChunkX()){

				
					cavetype.generateWall(gen, random, pos, pos.getY()/(float)chunkscan.surfaceAvg, pos.getY() - (int)cave.getAvgFloor());
				}
				else {
					cavetype.generateWall(gen, random, pos, pos.getY()/(float)chunkscan.surfaceAvg, pos.getY() - (int)cave.getAvgFloor());
				}
			}

			Iterator<AdjPos> adjacentIterator = cave.adjacentWall.iterator();
			AdjPos adj;
			while (adjacentIterator.hasNext()){
				adj = adjacentIterator.next();

				cavetype.generateAdjacentWall(gen, random, adj, adj.getY()/(float)chunkscan.surfaceAvg, adj.getY() - (int)cave.getAvgFloor());

			}

			Iterator<CavePosition> caveIterator= cave.cave.iterator();
			while (caveIterator.hasNext()) {
				position = caveIterator.next();
				if (!position.alreadyGenerated){
					
					cavetype.generateFloor(gen, random, position.getFloorPos(), (float) depth);
					cavetype.generateCeiling(gen, random, position.getCeilingPos(), (float) depth);

					if (random.nextInt(100) < cavetype.ceilingaddonchance+(1-depth)*5)
					{
						cavetype.generateCeilingAddons(gen, random, position.getCeilingPos().down(), (float) depth);
					}
					if (random.nextInt(100) < cavetype.flooraddonchance+(1-depth)*5)
					{
						cavetype.generateFloorAddons(gen, random, position.getFloorPos().up(), (float) depth);
					}


					position.alreadyGenerated = true;
				}
			}
		}
	}	
}
