//
//  GameView.swift
//  iosApp
//
//  Created by Hao Wu on 10/1/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SpriteKit
import SwiftUI

struct AsteroidTeilGameView: View {
    var setOpenLockGame: (Int) -> Void
    
    init(setOpenLockGame: @escaping (Int) -> Void = {value in }) {
        self.setOpenLockGame = setOpenLockGame
    }
    
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
            SpriteView(scene: AsteroidTeilGameScene(size: CGSize(width: 750, height: 1335)))
                .ignoresSafeArea()
        }
        
    }
}

struct AsteroidTeilGameView_Previews: PreviewProvider {
    static var previews: some View {
        AsteroidTeilGameView()
    }
}
