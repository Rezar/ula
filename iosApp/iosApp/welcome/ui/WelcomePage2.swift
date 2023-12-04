//
//  WelcomePage2.swift
//  iosApp
//
//  Created by Hao Wu on 9/18/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI

var leftPartWeight = 4.5
var middlePartWeight = 1.0
var rightPartWeight = 4.5

struct WelcomePage2: View {
    var onPreviousButtonClicked: () -> Void = {}
    var onNextButtonClicked: () -> Void = {}
    
    var body: some View {
        VStack(alignment: .center) {
            VStack {
                HStack {
                    Text("Ok?")
                        .font(.WelcomeTitle)
                        .foregroundColor(.red)
                }
                
                Spacer().frame(height: 60)
                
                HStack {
                    VStack {
                        Image("ic_stickman_4")
                            .resizable()
                            .frame(width: 120, height: 90)
                    }
                    VStack {
                        Image("arrows")
                            .resizable()
                            .frame(width: 50, height: 25)
                    }
                    VStack {
                        Image("stickman_draft_monster_5aug2014")
                            .resizable()
                            .frame(width: 60, height: 80)
                    }
                }
                
                Spacer().frame(height: 60)
                
                HStack {
                    VStack {
                        Image("bed")
                            .resizable()
                            .frame(width: 120, height: 50)
                    }
                    VStack {
                        Image("arrows")
                            .resizable()
                            .frame(width: 50, height: 25)
                    }
                    VStack {
                        Image("stickman_fat")
                            .resizable()
                            .frame(width: 50, height: 70)
                    }
                }
            }
            .padding(.leading, 20)
            .padding(.trailing, 20)
            .padding(.top, 50)
            .padding(.bottom, 0)
            
            Spacer()
            
            HStack() {
                BackButton(
                    onClick: {
                        onPreviousButtonClicked()
                    }
                )
                
                Spacer()
                
                NextButton(
                    onClick: {
                        onNextButtonClicked()
                    }
                )
            }
            .padding(.horizontal, 10)
        }
    }
}

struct WelcomePage2_Previews: PreviewProvider {
    static var previews: some View {
        WelcomePage2(
            onPreviousButtonClicked: {},
            onNextButtonClicked: {}
        )
    }
}
