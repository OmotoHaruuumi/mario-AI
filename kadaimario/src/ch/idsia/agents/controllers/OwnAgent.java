/*
 * Copyright (c) 2009-2010, Sergey Karakovskiy and Julian Togelius
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Mario AI nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package ch.idsia.agents.controllers;

import ch.idsia.agents.Agent;
import ch.idsia.benchmark.mario.engine.GeneralizerLevelScene;
import ch.idsia.benchmark.mario.engine.sprites.Mario;
import ch.idsia.benchmark.mario.environments.Environment;
import ch.idsia.tools.*;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Karakovskiy, sergey.karakovskiy@gmail.com
 * Date: Apr 8, 2009
 * Time: 4:03:46 AM
 */

public class OwnAgent extends BasicMarioAIAgent implements Agent
{
int trueJumpCounter = 0;
int trueSpeedCounter = 0;

public OwnAgent()
{
    super("OwnAgent");
    reset();
    int trueJumpCounter = 0;
    int trueSpeedCounter = 0;

}

public void reset()
{
	action = new boolean[Environment.numberOfKeys];
	trueJumpCounter = 0;
    trueSpeedCounter = 0;
}

public boolean[] getAction()
{
	if(action[Mario.KEY_LEFT]&& !isObstacle(marioEgoRow,marioEgoCol-1)) {
		if(!jumpcheck(marioEgoRow,marioEgoCol-1)) {
			LW();
		}else {RW();}
	}
	else if((action[Mario.KEY_RIGHT]&&(jumpcheck(marioEgoRow,marioEgoCol+4)))||(isObstacle(marioEgoRow,marioEgoCol+1))) {
    	RJ();}  
	
	else if(!isObstacle(marioEgoRow,marioEgoCol+1)&&jumpcheck(marioEgoRow,marioEgoCol+1)) {
	    	RW();}
	    
    else LW();
		
	
	return action;
}


public boolean isObstacle(int r, int c){
return getReceptiveFieldCellValue(r,
c)==GeneralizerLevelScene.BRICK
|| getReceptiveFieldCellValue(r,
c)==GeneralizerLevelScene.BORDER_CANNOT_PASS_THROUGH
|| getReceptiveFieldCellValue(r,
c)==GeneralizerLevelScene.FLOWER_POT_OR_CANNON
|| getReceptiveFieldCellValue(r,
c)==GeneralizerLevelScene.LADDER;
}

public boolean jumpcheck(int r,int c) {
	boolean jumpcheck=false;
	
	for(int i=1;i<10;i++) {
		if(isObstacle(r+i  ,c)){
			jumpcheck=true;
			break;
		}
	}
	return jumpcheck;
}

public void RJ(){
	action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;
	action[Mario.KEY_RIGHT] = true;
	action[Mario.KEY_LEFT]=false;
}

public void RW() {
	action[Mario.KEY_JUMP] = false;
	action[Mario.KEY_RIGHT] = true;
	action[Mario.KEY_LEFT]=false;
}

public void LW() {
	action[Mario.KEY_JUMP] = false;
	action[Mario.KEY_RIGHT] = false;
	action[Mario.KEY_LEFT]=true;
}

}