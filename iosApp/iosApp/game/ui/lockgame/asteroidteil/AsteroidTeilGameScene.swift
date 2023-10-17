//
//  GameScene.swift
//  iosApp
//
//  Created by Hao Wu on 10/1/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import SpriteKit
import GameKit

class AsteroidTeilGameScene: SKScene, SKPhysicsContactDelegate {
    
    var player = SKSpriteNode(imageNamed: "plane")
    
    let backGround = SKSpriteNode(imageNamed: "blue")
    
    let sterne = SKEmitterNode(fileNamed: "sterne")
    
    var asteroidTimer = Timer()
    
    var scoreTimer = Timer()
    
    let scoreLabel = SKLabelNode(fontNamed: "DJBFirstGradeTeacher")
    
    var score = 0 {
        didSet {
            scoreLabel.text = "Score \(score)"
        }
    }
    
    override func didMove(to view: SKView) {
        anchorPoint = .zero
        physicsWorld.contactDelegate = self
        
        backGround.position = CGPoint(x: size.width / 2, y: size.height / 2)
        backGround.size = self.size
        backGround.zPosition = -10
        addChild(backGround)
        
        sterne?.position = CGPoint(x: size.width / 2, y: size.height / 2)
        sterne?.zPosition = 5
        addChild(sterne!)
        
        scoreLabel.position = CGPoint(x: size.width / 2, y: size.height / 1.1)
        scoreLabel.zPosition = 8
        addChild(scoreLabel)
        
        score = 0
        
        player.position = CGPoint(x: size.width / 2, y: size.height / 8)
        player.zPosition = 10
        player.physicsBody = SKPhysicsBody(rectangleOf: player.size)
        player.physicsBody?.affectedByGravity = false
        player.physicsBody?.allowsRotation = false
        player.physicsBody?.categoryBitMask = 1
        player.physicsBody?.contactTestBitMask = 0
        addChild(player)
        
        asteroidTimer = .scheduledTimer(timeInterval: 1, target: self, selector: #selector(createAsteroid), userInfo: nil, repeats: true)
        
        scoreTimer = .scheduledTimer(timeInterval: 1, target: self, selector: #selector(punke), userInfo: nil, repeats: true)
    }
    
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
        for touch in touches {
            let location = touch.location(in: self)
            
            player.position.x = location.x
        }
    }
    
    @objc func createAsteroid() {
        let zufallsZahl = GKRandomDistribution(lowestValue: 10, highestValue: 740)
        
        let asteroid = SKSpriteNode(imageNamed: "asteroid")
        
        asteroid.position = CGPoint(x: zufallsZahl.nextInt(), y: 1400)
        asteroid.zPosition = 9
        asteroid.physicsBody = SKPhysicsBody(circleOfRadius: asteroid.size.width / 2)
        asteroid.physicsBody?.contactTestBitMask = 1
        asteroid.physicsBody?.collisionBitMask = 0
        addChild(asteroid)
    }
    
    func didBegin(_ contact: SKPhysicsContact) {
        guard let nodeA = contact.bodyA.node else {return}
        guard let nodeB = contact.bodyB.node else {return}
        
        if nodeA == player {
            playerRemote(node: nodeB)
        } else {
            playerRemote(node: nodeA)
        }
    }
    
    func playerRemote(node: SKNode) {
        player.removeFromParent()
        
        gameOver()
    }
    
    func gameOver() {
        let gameOverLabel = SKLabelNode(fontNamed: "DJBFirstGradeTeacher")
        gameOverLabel.position = CGPoint(x: size.width / 2, y: size.height / 2)
        gameOverLabel.zPosition = 10
        gameOverLabel.text = "Game Over"
        gameOverLabel.fontSize = 60
        addChild(gameOverLabel)
        
        let explo = SKEmitterNode(fileNamed: "explo")
        explo?.position = player.position
        explo?.zPosition = 9
        addChild(explo!)
    }
    
    @objc func punke() {
        score += 1
    }
}
