//
//  TappyBird.swift
//  iosApp
//
//  Created by Hao Wu on 10/1/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct TappyBird: View {
    var setOpenLockGame: (Int) -> Void
    
    init(setOpenLockGame: @escaping (Int) -> Void = {value in }) {
        self.setOpenLockGame = setOpenLockGame
    }
    
    let farben = [Color("ColorTappyBird1"), Color("ColorTappyBird2")]
    
    var body: some View {
        NavigationView {
            ZStack {
                VStack {
                    Spacer()
                    Text("Tappy Bird")
                        .font(.AppTitle)
                    Spacer()
                    Image("bird")
                    Spacer()
                    
                    NavigationLink(
                        destination: TappyBirdGameView(setOpenLockGame: setOpenLockGame),
                        label: {
                            Text("Start Game")
                                .font(.AppBody)
                                .foregroundColor(.black)
                        })
                    
                    Button(
                        "Back",
                        action: {
                            setOpenLockGame(0)
                        })
                        .foregroundColor(.black)
                        .font(.AppBody)
                    Spacer()
                }
            }
            .frame(width: 950, height: 440, alignment: .center)
            .background(LinearGradient(gradient: Gradient(colors: farben), startPoint: .bottom, endPoint: .top))
            .navigationBarHidden(true)
            .navigationBarBackButtonHidden(true)
        }
        .navigationViewStyle(StackNavigationViewStyle())
        .ignoresSafeArea()
        .navigationBarBackButtonHidden(true)
        .navigationBarHidden(true)
    }
}

struct TappyBird_Previews: PreviewProvider {
    static var previews: some View {
        TappyBird().previewLayout(.fixed(width: 950, height: 420))
    }
}

