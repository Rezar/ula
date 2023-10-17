//
//  TappyBirdGameView.swift
//  iosApp
//
//  Created by Hao Wu on 10/1/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import SpriteKit

struct TappyBirdGameView: View {
    var setOpenLockGame: (Int) -> Void
    
    init(setOpenLockGame: @escaping (Int) -> Void = {value in }) {
        self.setOpenLockGame = setOpenLockGame
    }
    
    @StateObject private var game = TappyBirdGameScene()
    var body: some View {
        ZStack {
            HStack {
                SpriteView(scene: game)
                    .ignoresSafeArea()
            }
            .navigationBarHidden(true)
            .navigationBarBackButtonHidden(true)
            .ignoresSafeArea()
            
            if game.isGameOver {
                NavigationLink(
                    destination: TappyBird(setOpenLockGame: setOpenLockGame),
                    label: {
                        Text("Back")
                            .font(.AppTitle)
                            .foregroundColor(.black)
                    }
                )
                .offset(y: 100)
            }
            
            Text("Score \(game.score)")
                .font(.AppBody)
                .foregroundColor(.black)
                .offset(y: -150)
            Spacer()
        }
        .ignoresSafeArea()
    }
}

struct TappyBirdGameView_Previews: PreviewProvider {
    static var previews: some View {
        TappyBirdGameView()
    }
}

