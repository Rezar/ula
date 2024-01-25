//
//  SpaceInvaders.swift
//  iosApp
//
//  Created by Hao Wu on 10/2/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import SpriteKit

struct SpaceInvaders: View {
    var setOpenLockGame: (Int) -> Void
    
    init(setOpenLockGame: @escaping (Int) -> Void = {value in }) {
        self.setOpenLockGame = setOpenLockGame
    }
    var scene = SKScene(fileNamed: "Game.sks")!
    
    var body: some View {
        ZStack {
            VStack {
                HStack {
                    Button(
                        "Back",
                        action: {
                            setOpenLockGame(0)
                        })
                        .foregroundColor(.white)
                        .font(.AppBody)
                        .padding(.leading, 20)
                        
                    
                    Spacer()
                }
                
                Spacer()
                    
            }.zIndex(1)
            SpriteView(scene: scene)
                .ignoresSafeArea()
        }
        
        
    }
}

struct SpaceInvaders_Previews: PreviewProvider {
    static var previews: some View {
        SpaceInvaders()
    }
}

