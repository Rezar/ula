//
//  AsteroidTeil.swift
//  iosApp
//
//  Created by Hao Wu on 10/1/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import SpriteKit

struct AsteroidTeil: View {
    let farben: [Color] = [Color("Color1"), Color("Color2")]
    
    var body: some View {
        NavigationView {
            ZStack {
                VStack {
                    Text("Asteroid")
                        .font(.AppTitle)
                        .foregroundColor(.red)
                        .offset(y: -300)
                }
                
                HStack {
                    Image("asteroid")
                        .offset(x: 30, y: -170)
                    Image("asteroid")
                        .offset(x: 100, y: -170)
                    Image("asteroid")
                        .offset(x: -120, y: -50)
                }
                
                Image("plane")
                    .offset(y: 100)
                
                NavigationLink (
                    destination: Text("Destination"),
                    label: {
                        Text("Start Game")
                            .font(.AppBody)
                            .foregroundColor(.red)
                            .offset(y: 150)
                    }
                )
            }
            .frame(width: 750, height: 1200, alignment: .center)
            .background(.white)
        }
    }
}

struct AsteroidTeil_Previews: PreviewProvider {
    static var previews: some View {
        AsteroidTeil()
    }
}
