//
//  GameOverScene.swift
//  iosApp
//
//  Created by Hao Wu on 10/3/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SpriteKit

class GameOverScene: SKScene {
    let gameOver = SKLabelNode(fontNamed: "DJBFirstGradeTeacher")
    
    override func didMove(to view: SKView) {
        backgroundColor = .white
        
        gameOver.text = "Game Over"
        gameOver.fontSize = 60
        gameOver.fontColor = .red
        gameOver.position = CGPoint(x: size.width / 2, y: size.height / 2)
        gameOver.setScale(0.6)
        gameOver.zPosition = 5
        addChild(gameOver)
        
        let fire = SKEmitterNode(fileNamed: "GameOverFire")
        fire?.position = CGPoint(x: size.width / 2, y: size.height / 2)
        fire?.zPosition = 4
        addChild(fire!)
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        let scene = BreakoutGameScene(size: self.size)
        let transition = SKTransition.flipVertical(withDuration: 2)
        
        self.view?.presentScene(scene, transition: transition)
    }
}
