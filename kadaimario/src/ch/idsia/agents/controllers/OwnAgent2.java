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
import ch.idsia.benchmark.mario.engine.sprites.Sprite;
import ch.idsia.benchmark.mario.environments.Environment;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Karakovskiy, sergey.karakovskiy@gmail.com
 * Date: Apr 8, 2009
 * Time: 4:03:46 AM
 */

public class OwnAgent2 extends BasicMarioAIAgent implements Agent
{
int fire_counter = 0;

public OwnAgent2()
{
    super("OwnAgent");
    reset();
}

public void reset()
{
    action = new boolean[Environment.numberOfKeys];
}

public boolean[] getAction()
{
		
	
	//敵が自分より高いところにいる場合
      if(isEnemies(marioEgoRow-1,marioEgoCol)
    		||isEnemies(marioEgoRow-1,marioEgoCol+1)
  			||isEnemies(marioEgoRow-1,marioEgoCol+2) 
			||isEnemies(marioEgoRow-2,marioEgoCol)
			||isEnemies(marioEgoRow-2,marioEgoCol+1)
			||isEnemies(marioEgoRow-2,marioEgoCol+2)
			||isEnemies(marioEgoRow-2,marioEgoCol+3)
			||isEnemies(marioEgoRow-3,marioEgoCol)
    	    ||isEnemies(marioEgoRow-3,marioEgoCol+1)
    	    ||isEnemies(marioEgoRow-3,marioEgoCol+2)
    		||isEnemies(marioEgoRow-4,marioEgoCol)
  			||isEnemies(marioEgoRow-4,marioEgoCol+1))
			 {
		LDW();
	}
	
	
	  
	  
	  
	  //敵が目の前にいる場合
	else if(isEnemies(marioEgoRow,marioEgoCol+1)) {
		VJ();
	}
	else if(isEnemies(marioEgoRow,marioEgoCol+2)){
		//特殊処理
		if(isEnemies(marioEgoRow,marioEgoCol+4)){
			VJ();
		}
		else {
		RJ();
		}
	}
	
	//前に障害物がある時の処理
	else if(isObstacle(marioEgoRow,marioEgoCol+1)) {
		RJ();
	}
	
	//少し先に敵がいる場合の処理
	else if(marioMode==2 && (isEnemies(marioEgoRow,marioEgoCol+3)
		    ||isEnemies(marioEgoRow,marioEgoCol+4)
		    ||isEnemies(marioEgoRow,marioEgoCol+5)))
	{
    	Attack();
    }
	
	//谷に落ちるとき
	else if(marioMode==2  && isMarioOnGround && (!isObstacle(marioEgoRow+1,marioEgoCol) || !isObstacle(marioEgoRow+1,marioEgoCol+2))&& fire_counter==0) {
		Attack();
		fire_counter=fire_counter+1;
	}
	
	
	//基本行動
	else if(jumpcheck(marioEgoRow,marioEgoCol+1)) {
    	RW();
    }
	
     //fire_counterが回っている時
      if(fire_counter>0) {
		  if(fire_counter!=3) {
			  Attack();
			  fire_counter=fire_counter+1;
		  }
		  else {
			  RW();
			  fire_counter=0;
		  }
	  }
      
      
      
    System.out.println(fire_counter);
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

public boolean isEnemies(int r,int c) {
	return getEnemiesCellValue(r,c)==Sprite.KIND_GOOMBA;
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
	action[Mario.KEY_RIGHT] = isMarioAbleToJump || !isMarioOnGround;
	action[Mario.KEY_LEFT]=false;
	action[Mario.KEY_SPEED]=false;                   
}

public void RW() {
	action[Mario.KEY_JUMP] = false;
	action[Mario.KEY_RIGHT] = true;
	action[Mario.KEY_LEFT]=false;
	action[Mario.KEY_SPEED]=false;
}

public void LW() {
	action[Mario.KEY_JUMP] = false;
	action[Mario.KEY_RIGHT] = false;
	action[Mario.KEY_LEFT]=true;
	action[Mario.KEY_SPEED]=false;
}

public void LDW() {
	action[Mario.KEY_JUMP] = false;
	action[Mario.KEY_RIGHT] = false;
	action[Mario.KEY_LEFT]=true;
	action[Mario.KEY_SPEED]=true;
}


public void VJ() {
	action[Mario.KEY_JUMP] = isMarioAbleToJump|| !isMarioOnGround;;
	action[Mario.KEY_RIGHT] = false;
	action[Mario.KEY_LEFT]=false;
	action[Mario.KEY_SPEED]=false;
}

public void Attack() {
	action[Mario.KEY_JUMP] = false;
	action[Mario.KEY_RIGHT] = false;
	action[Mario.KEY_LEFT]=false;
	action[Mario.KEY_SPEED]= isMarioAbleToShoot && isMarioOnGround;
}



}