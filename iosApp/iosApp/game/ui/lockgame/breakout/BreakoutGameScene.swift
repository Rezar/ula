//
//  BreakoutGameScene.swift
//  iosApp
//
//  Created by Hao Wu on 10/3/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SpriteKit
import GameKit

class BreakoutGameScene: SKScene, SKPhysicsContactDelegate {
    let background = SKSpriteNode(imageNamed: "bg_layer3")
    let paddel = SKSpriteNode(imageNamed: "paddleBlue")
    let ball = SKSpriteNode(imageNamed: "ballBlue")
    
    enum bitmasks: UInt32 {
        case frame = 0b1
        case paddel = 0b10
        case stone = 0b100
        case ball = 0b1000
    }
    
    override func didMove(to view: SKView){
        scene?.size = view.bounds.size
        scene?.scaleMode = .aspectFit
        physicsWorld.gravity = .zero
        physicsWorld.contactDelegate = self
        self.isPaused = true
        
        background.position = CGPoint(x: size.width / 2, y: size.height / 2 - 300)
        background.zPosition = 1
        background.setScale(0.25)
        addChild(background)
        backgroundColor = .white
        
        paddel.position = CGPoint(x: size.width / 2, y: 25)
        paddel.zPosition = 10
        paddel.setScale(0.25)
        paddel.physicsBody = SKPhysicsBody(rectangleOf: paddel.size)
        paddel.physicsBody?.friction = 0
        paddel.physicsBody?.allowsRotation = false
        paddel.physicsBody?.restitution = 1
        paddel.physicsBody?.isDynamic = false
        paddel.physicsBody?.categoryBitMask = bitmasks.paddel.rawValue
        paddel.physicsBody?.contactTestBitMask = bitmasks.ball.rawValue
        paddel.physicsBody?.collisionBitMask = bitmasks.ball.rawValue
        addChild(paddel)
        
        ball.position.x = paddel.position.x
        ball.position.y = paddel.position.y + ball.size.height - 75
        ball.zPosition = 10
        ball.setScale(0.30)
        ball.physicsBody = SKPhysicsBody(circleOfRadius: ball.size.height / 2)
        ball.physicsBody?.friction = 0
        ball.physicsBody?.restitution = 1
        ball.physicsBody?.linearDamping = 0
        ball.physicsBody?.angularDamping = 0
        ball.physicsBody?.allowsRotation = false
        ball.physicsBody?.categoryBitMask = bitmasks.ball.rawValue
        ball.physicsBody?.contactTestBitMask = bitmasks.paddel.rawValue | bitmasks.frame.rawValue | bitmasks.stone.rawValue
        ball.physicsBody?.collisionBitMask = bitmasks.paddel.rawValue | bitmasks.frame.rawValue | bitmasks.stone.rawValue
        addChild(ball)
        
        ball.physicsBody?.applyImpulse(CGVector(dx: 10, dy: 10))
        
        let frame = SKPhysicsBody(edgeLoopFrom: self.frame)
        frame.friction = 0
        frame.categoryBitMask = bitmasks.frame.rawValue
        frame.contactTestBitMask = bitmasks.ball.rawValue
        frame.collisionBitMask = bitmasks.ball.rawValue
        self.physicsBody = frame
        
        makeStones(reihe: 11, bitmask: 0b10, y: 800, name: "red_rect")
        makeStones(reihe: 11, bitmask: 0b10, y: 835, name: "purple_rect")
        makeStones(reihe: 11, bitmask: 0b10, y: 870, name: "green_rect")
    }
    
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
        for touch in touches {
            let lokation = touch.location(in: self)
            
            paddel.position.x = lokation.x
            
        }
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.isPaused = false
    }
    
    override func update(_ currentTime: TimeInterval) {
        if paddel.position.x < 50 {
            paddel.position.x = 50
        }
        
        if paddel.position.x > self.size.width - paddel.size.width / 2 {
            paddel.position.x = self.size.width - paddel.size.width / 2
        }
    }

    func makeStones(reihe: Int, bitmask: UInt32, y: Int, name: String) {
        for i in 1...reihe {
            let stone = SKSpriteNode(imageNamed: name)
//            stone.setScale(0.3)
            stone.position = CGPoint(x:65 + i * Int(stone.size.width), y: y - 300)
            stone.zPosition = 10
            stone.name = "Stone" + String(i)
            stone.physicsBody = SKPhysicsBody(rectangleOf: stone.size)
            stone.physicsBody?.friction = 0
            stone.physicsBody?.restitution = 1
            stone.physicsBody?.allowsRotation = false
            stone.physicsBody?.isDynamic = false
            stone.physicsBody?.categoryBitMask = bitmasks.stone.rawValue
            stone.physicsBody?.contactTestBitMask = bitmasks.ball.rawValue
            stone.physicsBody?.collisionBitMask = bitmasks.ball.rawValue
            addChild(stone)
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
        
        if contactA.categoryBitMask == bitmasks.stone.rawValue && contactB.categoryBitMask == bitmasks.ball.rawValue {
            contactA.node?.removeFromParent()
        }
        
        if contactA.categoryBitMask == bitmasks.paddel.rawValue && contactB.categoryBitMask == bitmasks.ball.rawValue {
            if contactB.node!.position.x <= contactA.node!.frame.midX - 5 {
                contactB.node?.physicsBody?.velocity = CGVector(dx: 0, dy: 0)
                contactB.node?.physicsBody?.applyImpulse(CGVector(dx: -10, dy: 10))
            }
            
            if contactB.node!.position.x >= contactA.node!.frame.midX + 5 {
                contactB.node?.physicsBody?.velocity = CGVector(dx: 0, dy: 0)
                contactB.node?.physicsBody?.applyImpulse(CGVector(dx: 10, dy: 10))
            }
        }
        
        if contactA.categoryBitMask == bitmasks.frame.rawValue && contactB.categoryBitMask == bitmasks.ball.rawValue {
            let yPos = contact.contactPoint.y
            
            if yPos <= self.frame.minY + 10 {
                gameOver()
            }
        }
    }
    
    func gameOver() {
        let gameOverScene = GameOverScene(size: self.size)
        let transition = SKTransition.flipHorizontal(withDuration: 2)
        
        self.view?.presentScene(gameOverScene, transition: transition)
        
        background.removeFromParent()
        paddel.removeFromParent()
        ball.removeFromParent()
    }
}

