//
//  Label.swift
//  iosApp
//
//  Created by Hao Wu on 9/30/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct Circle: View {
    var body: some View {
        VStack {
//            Circle()
//                .strokeBorder(Color.blue,lineWidth: 4)
//                .background(Circle().foregroundColor(Color.red))
        }
        .frame(width: Default.label.circle.size, height: Default.label.circle.size)
//        Circle()
//            .stroke(Color.green, lineWidth: 3)

    }
}

struct Cross: View {
    var body: some View {
        VStack {
            GeometryReader { geometry in
                Path { path in
                    let start = CGPoint(x: 0, y: 0)
                    let end = CGPoint(x: geometry.size.width, y: geometry.size.height)
                    path.move(to: start)
                    path.addLine(to: end)
                }
                .stroke(Default.label.cross.color, lineWidth: Default.label.cross.thickness)
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                
                Path { path in
                    let start = CGPoint(x: 0, y: geometry.size.height)
                    let end = CGPoint(x: geometry.size.width, y: 0)
                    path.move(to: start)
                    path.addLine(to: end)
                }
                .stroke(Default.label.cross.color, lineWidth: Default.label.cross.thickness)
                .frame(maxWidth: .infinity, maxHeight: .infinity)
            }
        }
        .frame(width: Default.label.cross.size, height: Default.label.cross.size)
    }
}

struct Label_Previews: PreviewProvider {
    static var previews: some View {
//        Cross()
        Circle()
    }
}
