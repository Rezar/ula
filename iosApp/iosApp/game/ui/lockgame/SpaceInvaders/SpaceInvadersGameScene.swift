//
//  SpaceInvadersGameScene.swift
//  iosApp
//
//  Created by Hao Wu on 10/2/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SpriteKit

class SpaceInvadersGameScene: SKScene, SKPhysicsContactDelegate {
    
    var player = SKSpriteNode(imageNamed: "playership")
    
    var fireTexture = SKTexture(imageNamed: "fire")
    
    var aliensArray = [SKNode]()
    
    var fireTimer = 60
    
    var scoreTimer = Timer()
    
    let scoreLabel = SKLabelNode(fontNamed: "DJBFirstGradeTeacher")
    
    var score = 0 {
        didSet {
            scoreLabel.text = "Score \(score)"
        }
    }
    
    enum bitMasks: UInt32 {
        case player = 0b1
        case alien = 0b10
        case fire = 0b100
    }
    
    override func didMove(to view: SKView) {
        scene?.size = CGSize(width: 750, height: 1334)
        scene?.scaleMode = .aspectFill
         
        physicsWorld.contactDelegate = self
        
        for node in self.children {
            if node.name == "alien" {
                aliensArray.append(node)
            }
        }
        
        scoreLabel.position = CGPoint(x: size.width / 2, y: size.height / 1.1)
        scoreLabel.zPosition = 8
        addChild(scoreLabel)
        
        score = 0
        
        addPlayer()
        
        scoreTimer = .scheduledTimer(timeInterval: 1, target: self, selector: #selector(punke), userInfo: nil, repeats: true)
    }
    
    func addPlayer() {
        player.position = CGPoint(x: size.width / 2, y: 100)
        player.zPosition = 100
        player.setScale(0.2)
        player.physicsBody?.affectedByGravity = false
        player.physicsBody?.allowsRotation = false
        player.physicsBody?.categoryBitMask = bitMasks.player.rawValue
        player.physicsBody?.contactTestBitMask = bitMasks.alien.rawValue
        player.physicsBody?.collisionBitMask = bitMasks.alien.rawValue

        addChild(player)
    }
    
    func fire() {
        let fire = SKSpriteNode(imageNamed: "fire")
        fire.position.x = player.position.x + 60
        fire.position.y = player.position.y + 5
        fire.zPosition = 50
        fire.setScale(0.2)
        fire.physicsBody = SKPhysicsBody(texture: fireTexture, size: fire.size)
        fire.physicsBody?.affectedByGravity = false
        fire.physicsBody?.allowsRotation = false
        fire.physicsBody?.categoryBitMask = bitMasks.fire.rawValue
        fire.physicsBody?.contactTestBitMask = bitMasks.alien.rawValue
        fire.physicsBody?.collisionBitMask = bitMasks.alien.rawValue
        
        let fireAction = SKAction.moveTo(y: 850, duration: 2)
        let removeAction = SKAction.removeFromParent()
        
        let sequence = SKAction.sequence([fireAction, removeAction])
        
        fire.run(sequence)
        addChild(fire)
    }
    
    func alienFire() {
        let alienFire = SKSpriteNode(imageNamed: "fire")
        
        
        let randomNumber = Int(arc4random_uniform(UInt32(aliensArray.count)))
        let alien = aliensArray[randomNumber]
        
        alienFire.position.x = alien.position.x + 4
        alienFire.position.y = alien.position.y
        alienFire.zPosition = 50
        alienFire.setScale(0.2)
        
        let fireAction = SKAction.moveTo(y: -100, duration: 2)
        let removeAction = SKAction.removeFromParent()
        
        let sequence = SKAction.sequence([fireAction, removeAction])
        
        alienFire.run(sequence)
        addChild(alienFire)
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        for touch in touches {
            let location = touch.location(in: self)
            
            if self.nodes(at: location).contains(player) {
                fire()
            }
        }
    }
    
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
        for touch in touches {
            let location = touch.location(in: self)
            
            player.position.x = location.x
        }
    }
    
    func didBegin(_ contact: SKPhysicsContact) {
        let contactA: SKPhysicsBody
        let contactB: SKPhysicsBody
        
        if contact.bodyA.categoryBitMask < contact.bodyB.categoryBitMask {
            contactA = contact.bodyA
            contactB = contact.bodyB
        } else {
            contactA = contact.bodyB
            contactB = contact.bodyA
        }
        
        if contactA.categoryBitMask == bitMasks.alien.rawValue && contactB.categoryBitMask == bitMasks.fire.rawValue {
            particle(position: contactA.node!.position)
            
            
            for node in aliensArray {
                if node == contactA.node {
                    aliensArray.remove(at: aliensArray.firstIndex(of: node)!)
                }
            }
            contactA.node?.removeFromParent()
            contactB.node?.removeFromParent()
        }
        
//        guard let nodeA = contact.bodyA.node else {return}
//        guard let nodeB = contact.bodyB.node else {return}
//        
//        if nodeA == player {
//            playerRemote(node: nodeB)
//        } else {
//            playerRemote(node: nodeA)
//        }
    }
    
    func particle(position: CGPoint) {
        let particle = SKEmitterNode(fileNamed: "FireParticle.sks")
        
        particle?.position = position
        particle?.zPosition = 25
        particle?.setScale(0.5)
        
        addChild(particle!)
    }
    
    override func update(_ currentTime: TimeInterval) {
        fireTimer -= 1
        
        if fireTimer == 0 {
            alienFire()
            
            fireTimer = 60
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
