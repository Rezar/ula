//
//  BaseBoard.swift
//  iosApp
//
//  Created by Hao Wu on 9/30/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct BaseBoard: View {
    var body: some View {
        VStack {
            GeometryReader { geometry in
                Path { path in
                    let start = CGPoint(x: geometry.size.width / 3, y: 0)
                    let end = CGPoint(x: geometry.size.width / 3, y: geometry.size.height)
                    path.move(to: start)
                    path.addLine(to: end)
                }
                .stroke(Default.boardLine.color, lineWidth: Default.boardLine.thickness)
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                
                Path { path in
                    let start = CGPoint(x: geometry.size.width * 2 / 3, y: 0)
                    let end = CGPoint(x: geometry.size.width * 2 / 3, y: geometry.size.height)
                    path.move(to: start)
                    path.addLine(to: end)
                }
                .stroke(Default.boardLine.color, lineWidth: Default.boardLine.thickness)
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                
                Path { path in
                    let start = CGPoint(x: 0, y: geometry.size.height / 3)
                    let end = CGPoint(x: geometry.size.width, y: geometry.size.height / 3)
                    path.move(to: start)
                    path.addLine(to: end)
                }
                .stroke(Default.boardLine.color, lineWidth: Default.boardLine.thickness)
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                
                Path { path in
                    let start = CGPoint(x: 0, y: geometry.size.height * 2 / 3)
                    let end = CGPoint(x: geometry.size.width, y: geometry.size.height * 2 / 3)
                    path.move(to: start)
                    path.addLine(to: end)
                }
                .stroke(Default.boardLine.color, lineWidth: Default.boardLine.thickness)
                .frame(maxWidth: .infinity, maxHeight: .infinity)
            }
        }
        .frame(width: Default.board.size, height: Default.board.size)
    }
}

struct BaseBoard_Previews: PreviewProvider {
    static var previews: some View {
        BaseBoard()
    }
}
