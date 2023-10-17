//
//  GameScene.swift
//  iosApp
//
//  Created by Hao Wu on 10/1/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SpriteKit
import SwiftUI
import GameplayKit

class TappyBirdGameScene: SKScene, ObservableObject, SKPhysicsContactDelegate  {
    let player = SKSpriteNode(imageNamed: "bird")
    
    var playerAnimation = [SKTexture]()
    
    var touchesBegin = false
    
    var gegnerTimer = Timer()
    
    @Published var isGameOver = false
    
    @Published var score = 0
    
    var scoreTimer = Timer()
    
    override func didMove(to view: SKView) {
        self.size = CGSize(width: 812, height: 375)
        scene?.scaleMode = .fill
        
        anchorPoint = .zero
        physicsWorld.gravity = CGVector(dx: 0, dy: -3)
        physicsWorld.contactDelegate = self
        
        player.position = CGPoint(x: 40 + player.size.width / 2, y: size.height - player.size.height / 2)
        player.setScale(0.35)
        player.physicsBody = SKPhysicsBody(rectangleOf: player.size)
        player.physicsBody?.categoryBitMask = 1
        player.zPosition = 10
        addChild(player)
        
        // animation
        let textureAtlas = SKTextureAtlas(named: "Sprites")
        
        for i in 0..<textureAtlas.textureNames.count {
            let name = "bird"
            playerAnimation.append(textureAtlas.textureNamed(name))
        }
        
        moveBackGrounds(image: "backgroundgrass", y: 0, z: -5, duration: 10, needPhysics: false, size: self.size)
        
        player.run(SKAction.repeatForever(SKAction.animate(with: playerAnimation, timePerFrame: 0.14)))
        
        gegnerTimer = .scheduledTimer(timeInterval: 1, target: self, selector: #selector(gegnerErstellen), userInfo: nil, repeats: true)
        
        scoreTimer = .scheduledTimer(timeInterval: 1, target: self, selector: #selector(addScore), userInfo: nil, repeats: true)
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        touchesBegin = true
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        touchesBegin = false
    }
    
    override func update(_ currentTime: TimeInterval) {
        if touchesBegin {
            player.physicsBody?.velocity = CGVector(dx: 0, dy: 200)
        }
        
        if player.position.y > 340 {
            player.position.y = 340
        }
    }
    
    func moveBackGrounds(image: String, y: CGFloat, z: CGFloat, duration: Double, needPhysics: Bool, size: CGSize) {
        for i in 0...1 {
            let node = SKSpriteNode(imageNamed: image)
            
            node.anchorPoint = .zero
            node.position = CGPoint(x: size.width * CGFloat(i), y : y)
            node.size = size
            node.zPosition = z
            
            if needPhysics {
                node.physicsBody = SKPhysicsBody(rectangleOf: node.size)
                node.physicsBody?.isDynamic = false
                node.physicsBody?.contactTestBitMask = 1
                node.name = "gegner"
                
            }
            
            let move = SKAction.moveBy(x: -node.size.width, y: 0, duration: duration)
            
            let wrap = SKAction.moveBy(x: node.size.width, y: 0, duration: 0)
            
            let sequence = SKAction.sequence([move, wrap])
            let immer = SKAction.repeatForever(sequence)
            
            node.run(immer)
            
            addChild(node)
        }
    }
    
    @objc func gegnerErstellen() {
        let zufallsZahl = GKRandomDistribution(lowestValue: 10, highestValue: 360)
        
        let gegner = SKSpriteNode(imageNamed: "bomb")
        gegner.position = CGPoint(x: 800, y: zufallsZahl.nextInt())
        gegner.setScale(0.35)
        gegner.zPosition = 5
        gegner.physicsBody = SKPhysicsBody(rectangleOf: gegner.size)
        gegner.physicsBody?.isDynamic = false
        gegner.physicsBody?.contactTestBitMask = 1
        gegner.name = "gegner"
        
        addChild(gegner)
        
        let moveAktion = SKAction.moveTo(x: -30, duration: 10)
        let removeAktion = SKAction.removeFromParent()
        
        let aktions = SKAction.sequence([moveAktion, removeAktion])
        
        gegner.run(aktions)
        
        let textureAtlas = SKTextureAtlas(named: "animation")
        var gegnerAnimation = [SKTexture]()

        for i in 0..<textureAtlas.textureNames.count {
            let name = "bomb"
            gegnerAnimation.append(textureAtlas.textureNamed(name))
        }

        gegner.run(SKAction.repeatForever(SKAction.animate(with: gegnerAnimation, timePerFrame: 0.14)))
    }
    
    func playerHit(node: SKNode) {
        if node.name == "gegner" {
            player.removeFromParent()
            gameOver()
        }
    }
    
    func didBegin(_ contact: SKPhysicsContact) {
        guard let nodeA = contact.bodyA.node else {return}
        guard let nodeB = contact.bodyB.node else {return}
        
        if nodeA == player {
            playerHit(node: nodeB)
        }
        
        if nodeB == player {
            playerHit(node: nodeA)
        }
    }
    
    func gameOver() {
        gegnerTimer.invalidate()
        
        let gameOverLabel = SKLabelNode(fontNamed: "DJBFirstGradeTeacher")
        gameOverLabel.text = "Game Over"
        gameOverLabel.fontSize = 32
        gameOverLabel.fontColor = .red
        gameOverLabel.zPosition = 10
        gameOverLabel.position = CGPoint(x: size.width / 2, y: size.height / 2)
        
        addChild(gameOverLabel)
        
        isGameOver = true
    }
    
    @objc func addScore() {
        score += 1
    }
}
