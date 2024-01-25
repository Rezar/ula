//
//  DebugScreen.swift
//  iosApp
//
//  Created by Hao Wu on 8/19/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI

struct DebugScreen: View {
    
    @ObservedObject var debugViewModel: IOSDebugViewModel
    @ObservedObject var homeViewModel: IOSHomeViewModel
    
    init() {
        self.debugViewModel = IOSDebugViewModel()
        self.homeViewModel = IOSHomeViewModel()
    }
    
    let ageOptions = [
        "",
        DataSource.MonsterAgeOptions.egg.name,
        DataSource.MonsterAgeOptions.child.name,
        DataSource.MonsterAgeOptions.adult.name
    ]
    
    let bodyStatusOptions = [
        "",
        DataSource.MonsterBodyStatusOptions.na.name,
        DataSource.MonsterBodyStatusOptions.normal.name,
        DataSource.MonsterBodyStatusOptions.overweight.name,
        DataSource.MonsterBodyStatusOptions.fat.name,
        DataSource.MonsterBodyStatusOptions.fit.name,
    ]
    
    
    @State private var ageOptionSelected = ""
    @State private var bodyStatusOptionSelected = ""
    @State private var movieIdOptionSelected = ""
    
    var body: some View {
        
        VStack {
            Text("Debug and Stay ALIVE!")
                .font(.AppBody)
            
            Spacer()
            
            Picker("Age: ", selection: $ageOptionSelected) {
                ForEach(ageOptions, id: \.self) {
                    Text($0).font(.AppBody)
                }
            }.pickerStyle(.menu).font(.AppBody)
            
            Picker("Body Status: ", selection: $bodyStatusOptionSelected) {
                ForEach(bodyStatusOptions, id: \.self) {
                    Text($0).font(.AppBody)
                }
            }.pickerStyle(.menu).font(.AppBody)
            
            Picker("Movie ID: ", selection: $movieIdOptionSelected) {
                ForEach(debugViewModel.getMovieIdOptions(), id: \.self) {
                    Text($0).font(.AppBody)
                }
            }.pickerStyle(.menu).font(.AppBody)
            
            Spacer()
            
            Button("Change the movie! ", action: {

                if debugViewModel.isOnlyChangeId(ageSelection: ageOptionSelected, bodyStatusSelection: bodyStatusOptionSelected, movieSelection: movieIdOptionSelected) {
                    
                    homeViewModel.setId(id: movieIdOptionSelected)
                    print("age: ", ageOptionSelected)
                    print("body status: ", bodyStatusOptionSelected)
                    print("movie id: ", movieIdOptionSelected)
                    
                } else {
                    homeViewModel.setAgeAndBodyStatus(age: ageOptionSelected, bodyStatus: bodyStatusOptionSelected)
                    print("age: ", ageOptionSelected)
                    print("body status: ", bodyStatusOptionSelected)
                    print("movie id: ", movieIdOptionSelected)
                    
                }
            })
            .disabled(!debugViewModel.isEnableButton(ageSelection: ageOptionSelected, bodyStatusSelection: bodyStatusOptionSelected, movieSelection: movieIdOptionSelected))
            .font(.AppBody)
            
            Spacer()
            
        }
        
    }
    
}


//struct DebugScreen_Previews: PreviewProvider {
//    static var previews: some View {
//        DebugScreen()
//    }
//}
