//
//  Breatout.swift
//  iosApp
//
//  Created by Hao Wu on 10/3/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import SpriteKit

struct Breakout: View {
    var setOpenLockGame: (Int) -> Void
    
    init(setOpenLockGame: @escaping (Int) -> Void = {value in }) {
        self.setOpenLockGame = setOpenLockGame
    }
    
    var scene = BreakoutGameScene()

    var body: some View {
        ZStack {
            VStack {
                HStack {
                    Button(
                        "Back",
                        action: {
                            setOpenLockGame(0)
                        })
                        .foregroundColor(.black)
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


