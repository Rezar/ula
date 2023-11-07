//
//  TicTacToe.swift
//  iosApp
//
//  Created by Hao Wu on 9/30/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct TicTacToe: View {
    var setOpenLockGame: (Int) -> Void
    
    @StateObject var gameState = GameState()
    
    init(setOpenLockGame: @escaping (Int) -> Void) {
        self.setOpenLockGame = setOpenLockGame
    }
    
    var body: some View {
        let borderSize = CGFloat(5)
        
        HStack() {
            Button(
                "Back",
                action: {
                    setOpenLockGame(0)
                }
            )
            
            Spacer()
        }
        .padding(.leading, 20)
        
        Text(gameState.turnText())
            .font(.title)
            .bold()
            .padding()
        Spacer()
        
        Text(String(format: "Crosses: %d", gameState.crossesScore))
            .font(.title)
            .bold()
            .padding()
        
        VStack(spacing: borderSize) {
            ForEach(0...2, id: \.self) {
                row in
                HStack(spacing: borderSize) {
                    ForEach(0...2, id: \.self) {
                        column in
                        
                        let cell = gameState.board[row][column]
                        
                        Text(cell.displayTile())
                            .font(.system(size: 60))
                            .foregroundColor(cell.tileColor())
                            .bold()
                            .frame(maxWidth: .infinity, maxHeight: .infinity)
                            .aspectRatio(1, contentMode: .fit)
                            .background(Color.white)
                            .onTapGesture {
                                gameState.placeTile(row, column)
                            }
                    }
                }
                
            }
        }
        .background(Color.black)
        .padding()
        .alert(isPresented: $gameState.showAlert) {
            Alert(
                title: Text(gameState.alertMessage),
                dismissButton: .default(Text("Play Again?")) {
                    gameState.resetBoard()
                }
            )
        }
        
        Text(String(format: "Noughts: %d", gameState.noughtsScore))
            .font(.title)
            .bold()
            .padding()
        Spacer()
    }
}